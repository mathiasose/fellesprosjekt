
'''The Client class and code for creating and initiating a Client object. 

A Client object communicates with a running Server object from the server module by sending 
messages governed by the 'project protocol'. This protocol uses the json format. A client can 
decode and encode messages based on the protocol, and therefore able to both sendResponse and receive 
this kind of messages.

Project request protocol consists of four fields:

- 'user': Username or requested username of a client
- 'time': Date & time the message was made
- 'message': Message from entry window
- 'flag': Requesting special action apart from message push
    * 'login': Requesting login 
    * 'logout': Requesting logout

The Client class uses the console for displaying messages and notifications. To sendResponse a message 
a client will use tkinter entry window. Messages sent by a client will be shown in the console 
of all the connected clients.

'''


import socket
import json
import threading
import tkinter
from datetime import datetime


LOGIN = 'login'
EXIT = 'exit'

ENTER = 'enter'
LEAVE = 'leave'
STATUS = 'status'

NONE = 'none'


class Client(object):
    
    '''Client able to communicate with similar clients through the server module.
    
    Extends object Class.
    
    Methods:
    
    - __init__()
    - start(host, port)
    - message_write()
    - message_read()
    - sendResponse(data)
    - receive()
    - callback()
    
    Instance variables:
    
    - __connection
    - __login
    - __user
    - __root
    
    '''

    def __init__(self):
        '''Constructor. Sets up for TCP connection.'''
        
        self.__connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)   #Holds TCP socket
        self.__root = tkinter.Tk()      #Holds root to which add the tkinter components
        self.__user = None              #Holds username of the client
        self.__login = False            #Is True if Client is logged in with valid username
        
    
    
    def start(self, host, port):
        '''Initiate the client. Give control to the caller.
        
        Connect to a running Server instance. Host is local. Port is 8088.
        
        Initiates two threads in order to send and receive messages simultaneously. The client will run 
        until both threads are terminated. The connection is closed by the message_read() method.
        
        '''
        
        self.__connection.connect((host, port))
        print('Enter your username')
        
        #Output thread
        read_thread = threading.Thread(target = self.message_read)
        read_thread.start()
        
        #Input thread
        write_thread = threading.Thread(target = self.message_write)
        write_thread.start()
        
    
        
    def message_write(self):
        '''Thread for taking care of input from user. Initiates and controls entry window'''
        
        def callback():
            '''Call by pushing tkinter button. Set up message to the server.'''
            
            #Process login request if not logged in.
            if not self.__login:
                
                #Send disconnect request. Terminate input loop when message sent.
                if entryWindow.get() == 'exit' or entryWindow.get() == 'quit':
                    data = json.dumps({'user': self.__user, 
                                       'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                       'message': entryWindow.get(), 
                                       'flag': EXIT})
                    self.sendResponse(data)
                
                #Send username request
                else:
                    data = json.dumps({'user': self.__user, 
                                       'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                       'message': entryWindow.get(), 
                                       'flag': LOGIN})
                    self.sendResponse(data)
            
            #Process message if logged in.    
            else:
            
                #Send disconnect request. Terminate input loop when message sent.
                if entryWindow.get() == 'exit' or entryWindow.get() == 'quit':
                    data = json.dumps({'user': self.__user, 
                                       'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                       'message': entryWindow.get(), 
                                       'flag': EXIT})
                    self.sendResponse(data)
            
                #Send message to connected clients.
                else:
                    data = json.dumps({'user': self.__user, 
                                   'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                   'message': entryWindow.get(), 
                                   'flag': NONE})
                    self.sendResponse(data)
                
        
        #tkinter entry window
        entryWindow = tkinter.Entry(self.__root)
        entryWindow.pack()
        entryWindow.focus_set()
        
        #tkinter button
        button = tkinter.Button(self.__root, text = "OK", width = 10, command = callback)
        button.pack()
        
        #Activate tkinter components and loop until terminated
        tkinter.mainloop()
        
    
            
    def message_read(self):
        '''Thread for taking care of output. Prints to console window.'''

        #Login loop. Terminate once __login is True (username is valid)
        while not self.__login:
            data = self.receive()
            print(data['message'])
            
            #Successful login
            if (data['flag'] == ENTER):
                self.__user = data['user'] 
                self.__login = True
                
            #Disconnect without logging in
            elif (data['flag'] == LEAVE):
                break
        
        #Input loop. Terminate once __login is False
        while self.__login:
            
            #The server may send two json strings concatenated. Will cause error
            try:
                data = self.receive()
            
                #Successful logout
                if data['flag'] == LEAVE:
                    print(data['message'])
                    self.__login = False
                 
                #Received status notification
                elif data['flag'] == STATUS:
                    print(data['message'])
            
                #Received new message
                elif data['flag'] == NONE:
                    print(data['time'] + ' ' + data['user'] + ': ' + data['message'])
                    
            except:
                print('***Backlog error, two messages lost***')   
        
        self.__root.quit()
        self.__connection.close()



    def sendResponse(self, data):
        '''Encode and send data'''
        
        data = data.encode('utf-8')
        self.__connection.sendall(data)



    def receive(self):
        '''Decode and load received data according to protocol'''
        
        received_data = self.__connection.recv(1024)
        received_data = received_data.decode('utf-8')
        received_data = json.loads(received_data)
        return received_data
        
        


if __name__ == "__main__":
    '''Create Client object. Initiate it'''
    
    client = Client()
    client.start('localhost', 8088)
