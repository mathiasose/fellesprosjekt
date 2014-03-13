
import copy
import socketserver
import json
import eventDispatcher, event
import re


class ClientHandler(socketserver.BaseRequestHandler):
    
    dispatcher = eventDispatcher.EventDispatcher()
    
    def handle(self):
        
        self.event_dispatcher = self.dispatcher
        self.connection = self.request
        
        self.ip = self.client_address[0]
        self.port = self.client_address[1]
        
        print ('Client connected @' + self.ip + ':' + str(self.port))
        
        while True:    
            data = self.receive()
            
            if data['flag'] == 'disconnect':
                backlog.saveMsg(data)
                
                print ('Client disconnected @' + self.ip + ':' + str(self.port))
                
                self.event_dispatcher.remove_event_listener(event.NEW, self.sendNew)
                self.event_dispatcher.dispatch_event(event.Event(event.NEW, self))
                
                privateData = copy.deepcopy(data)
                privateData['message'] = 'now exiting...'
                self.sendData(privateData)
                return
            
            elif data['flag'] == 'login':
                if not userList.validUser(data['user']):
                    
                    privateData = copy.deepcopy(data)
                    privateData['message'] = 'invalid username'
                    self.sendData(privateData)
                else:
                    backlog.saveMsg(data)
                    userList.saveUser(data['user'])
                    
                    privateData = copy.deepcopy(data)
                    privateData['message'] = '[ ' + privateData['user'] + ' ] ' + 'Write \'exit\' or \'quit\' to log out'
                    self.sendData(privateData)
                    
                    self.sendBacklog()
                    
                    self.event_dispatcher.dispatch_event(event.Event(event.NEW, self))
                    self.event_dispatcher.add_event_listener(event.NEW, self.sendNew)
            
            else:
                backlog.saveMsg(data)
                
                print (data['time'] + ' ' + data['user'] + ': ' + data['message'])
                self.event_dispatcher.dispatch_event(event.Event(event.NEW, self))
        
    
    def sendBacklog(self):
        
        msgs = backlog.getMsgs()
        if not msgs:
            return
        
        if len(msgs) <= 20:
            for i in range(len(msgs)):
                self.sendLog(i)
            
        else:
            for i in range((len(msgs)-20), len(msgs)):
                self.sendLog(i)
                
                
    def sendData(self, data):
        if data:
            data = json.dumps(data)
            data = data.encode('utf-8')
            self.connection.sendall(data)
                
    
    def sendLog(self, i):
        data = backlog.getMsg(i)
        if data:
            data = json.dumps(data)
            data = data.encode('utf-8')
            self.connection.sendall(data)
    
    
    def sendNew(self):
        data = backlog.getMsg(-1)
        if data:
            data = json.dumps(data)
            data = data.encode('utf-8')
            self.connection.sendall(data)


    def receive(self):
        received_data = self.connection.recv(1024)
        received_data = received_data.decode('utf-8')
        print('check')
        received_data = json.loads(received_data)
        return received_data
                



class Backlog():

    def __init__(self):
        self.__messages = []
    
    def getMsgs(self):
        if self.__messages:
            return self.__messages
    
    def getMsg(self, i):
        if self.__messages:
            return self.__messages[i]
        
    def saveMsg(self, msg):
        self.__messages.append(msg)




class Userlist():
    
    def __init__(self):
        self.__users = []
        
    def getUser(self):
        if self.__users:
            return self.users
        
    def saveUser(self, user):
        self.__users.append(user)
        
    def validUser(self, username):
        if self.__users:
            return (not username in self.__users and re.match('^[A-Za-z0-9_]*$', username))
        else:
            return(re.match('^[A-Za-z0-9_]*$', username))
        
        
            

class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass




if __name__ == "__main__":
    HOST = 'localhost'
    PORT = 8088
    
    backlog = Backlog()
    userList = Userlist()
    server = ThreadedTCPServer((HOST, PORT), ClientHandler)
    server.serve_forever()
    