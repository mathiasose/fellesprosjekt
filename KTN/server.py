
'''Code for creating a threaded TCP server, and ClientHandler class for handling client connection

The server uses ThreadedTCPServer class with important logic to handle multiple client connections at 
the same time. Server host and port and a client handler is passed as arguments to create the 
instance. The ClientHandler class is initiated once connection to a client i established, and 
consists of logic responding to messages from that single client according to the 'project protocol'.

Project response protocol consists of four fields:

- 'user': Username of writing clients message or request
- 'time': Date & time the writing clients message or request was made
- 'message': Message to be displayed by reading clients
- 'flag': Which action to be performed and how message are to be displayed
    * 'enter': Enable client to receive chat activity
    * 'leave': Prompt client to disconnect
    * 'status': Prompt client to display status update
    
A joint backlog is created accessible to all ClientHandler instances, saving every public message 
(messages to be displayed to the majority of clients). Each ClientHandler instances communicates 
through an event dispatcher. 

When a new message is successfully sent from a user, it is first saved in the backlog. An event is 
then dispatched from the backlog telling all ClientHandler instances it has been updated. The 
receiving handler proceeds to access the backlog, load the message and forwards it to the connected 
clients. If a request is sent from a user the handler will both respond and save it as a public message 
in the backlog, prompting all client handlers to forward it.

'''


import copy
import socketserver
import json
import event
import re
import memory


LOGIN = 'login'
EXIT = 'exit'

ENTER = 'enter'
LEAVE = 'leave'
STATUS = 'status'

NONE = 'none'


class ClientHandler(socketserver.BaseRequestHandler):
    
    '''Handler to store and process messages and requests. Taking care of one connected client.
    
    Extends BaseRequestHandler class
    
    Methods:
    
    - handle()
    - sendBacklog()
    - sendLog(i)
    - sendNew()
    - sendResponse(data)
    - receive()
    - makeJson(data, flag, message, user)
    - validUser(username)    
    
    Class variables:
    
    - server_backlog
    - server_userlist
    - event_dispatcher
    
    '''
    
    event_dispatcher = event.EventDispatcher()                  #Holds the common event dispatcher
    server_backlog = memory.Backlog(event_dispatcher)           #Holds all public messages
    server_userlist = memory.Userlist()                         #Holds taken usernames
    
    def handle(self):
        '''Run when instanciated. Connect the client, handle messages and requests.
        
        The method listens to both the connected client and other ClientHandlers (i.e. backlog 
        activity).
        
        '''
        
        self.connection = self.request
        
        self.ip = self.client_address[0]
        self.port = self.client_address[1]
        
        print ('Client connected @' + self.ip + ':' + str(self.port))
        
        #Loop until LEAVE flag is sent
        while True:    
            data = self.receive()
            
            #Client requests disconnection
            if data['flag'] == EXIT:
                print ('Client disconnected @' + self.ip + ':' + str(self.port))
                
                #Response 
                replyData = self.makeJson(data, 
                                          LEAVE, 
                                          'now exiting...', 
                                          None)
                self.sendResponse(replyData)
                
                #Client is logged in
                if data['user']:
                    
                    #Remove listener
                    self.event_dispatcher.remove_event_listener(event.NEW, self.sendNew)
                
                    #Save and dispatch public message
                    logData = self.makeJson(data, 
                                            STATUS, 
                                            data['user'] + ' has left the chat', 
                                            None)
                    ClientHandler.server_backlog.saveMsg(logData)
                
                #Terminate handler
                return
            
            #Client requests login
            elif data['flag'] == LOGIN:
                
                #Invalid username
                if not self.validUser(data['message']):
                    
                    #Response
                    replyData = self.makeJson(data, 
                                              STATUS, 
                                              'invalid username', 
                                              None)
                    self.sendResponse(replyData)
                
                #Valid username
                else:
                    
                    #Add listener
                    self.event_dispatcher.add_event_listener(event.NEW, self.sendNew)
                    
                    #Response
                    replyData = self.makeJson(data, 
                                              ENTER, 
                                              '[ ' + data['message'] + ' ] ' + 'Write \'exit\' or \'quit\' to log out \n', 
                                              data['message'])
                    self.sendResponse(replyData)
                    
                    #Display backlog and save username
                    self.sendBacklog()
                    ClientHandler.server_userlist.saveUser(data['message'])
                    
                    #Save and dispatch public message
                    logData = self.makeJson(data, 
                                            STATUS, 
                                            data['message'] + ' has entered the chat', 
                                            data['message'])
                    ClientHandler.server_backlog.saveMsg(logData)
            
            #Client sends ordinary message
            elif data['flag'] == NONE:
                
                #Save and dispatch public message
                ClientHandler.server_backlog.saveMsg(data)
        
    
    def sendBacklog(self):
        '''Send the recent 40 or less messages from backlog.'''
        
        if ClientHandler.server_backlog.getMsgs():
                        
            if len(ClientHandler.server_backlog.getMsgs()) <= 40:
                for i in range(len(ClientHandler.server_backlog.getMsgs())):
                    self.sendLog(i)
            
            else:
                for i in range((len(ClientHandler.server_backlog.getMsgs())-40), len(ClientHandler.server_backlog.getMsgs())):
                    self.sendLog(i)
                
                
    def sendLog(self, i):
        '''Send a message at position i from backlog.'''
        
        if ClientHandler.server_backlog.getMsg(i):
            data = json.dumps(ClientHandler.server_backlog.getMsg(i))
            data = data.encode('utf-8')
            self.connection.sendall(data)
    
    
    def sendNew(self):
        '''Access when event is received from client. Send latest message in backlog.'''
        
        if ClientHandler.server_backlog.getMsg(-1):
            data = json.dumps(ClientHandler.server_backlog.getMsg(-1))
            data = data.encode('utf-8')
            self.connection.sendall(data)
            
            
    def sendResponse(self, data):
        '''Response message. Send the input data.'''
        
        if data:
            data = json.dumps(data)
            data = data.encode('utf-8')
            self.connection.sendall(data)
                
    
    def receive(self):
        '''Decode and load received data according to protocol'''
        
        received_data = self.connection.recv(1024)
        received_data = received_data.decode('utf-8')
        received_data = json.loads(received_data)
        return received_data
    
    
    def makeJson(self, data, flag, message, user):
        '''Construct new json. Make a copy of data and modify the specified fields.'''
        
        outData = copy.deepcopy(data)
        
        if flag:
            outData['flag'] = flag
        if message:
            outData['message'] = message
        if user:
            outData['user'] = user
        
        return outData
    
    
    def validUser(self, username):
        '''Validate passed username.'''
        
        validate = re.match('^[A-Za-z0-9_]*$', username)                    #Is alphanumeric
        validate = validate and len(username) >= 3                          #Is three or more letters
        validate = validate and len(username) <= 20                         #Is 20 or less letters
        if self.server_userlist.getUsers():
            validate = validate and not username in self.server_userlist.getUsers()    #Is not a duplicate
        
        return (validate)        
                        
        
            

class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    '''ThreadedTCPServer must extend ThreadingMixIn and TCPServer. No other functionality needed.'''
    
    pass




if __name__ == "__main__":
    '''Create ThreadedTCPServer object. Initiate it.'''
    
    HOST = 'localhost'
    PORT = 8088
    
    server = ThreadedTCPServer((HOST, PORT), ClientHandler)
    server.serve_forever()
    