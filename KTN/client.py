
'''The Client class and code for creating and initiating a Client object. 

A Client object communicates with a running Server object from the server module by sending 
messages governed by the 'project protocol'. This protocol uses the json format. A client can 
decode and encode messages based on the protocol, and therefore able to both sendData and receive 
this kind of messages.

Project protocol has four fields:

- 'user': Username or requested username of a client
- 'message': Message to be displayed
- 'time': Date & time the message was made
- 'flag': Notification of client attempting to log in or disconnect

The Client class uses the console for displaying messages and notifications. To sendData a message 
a client will use tkinter entry window. Messages sent by a client will be shown in the console 
of all the connected clients.

'''


import socket
import json
import threading
import tkinter
from datetime import datetime


class Client(object):
    
    '''A client able to communicate with similar clients through the Server class
    
    Methods:
    
    - __init__()
    - start(host, port)
    - message_write()
    - message_read()
    - sendData(data)
    - receive()
    - callback()
    
    Instance variables:
    
    - __connection
    - __login
    - __user
    
    '''

    def __init__(self):
        '''Constructor. Sets up for TCP connection.'''
        self.__connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)   #Holds the TCP socket
        self.__user = None      #Holds the username of the client
        self.__login = False    #Is True if the client is logged in with valid username
        
        
    def start(self, host, port):
        '''Initiates the client. Gives control to the caller.
        
        Connects to a running Server instance. The host is at the moment local host. The port is 8088.
        
        Initiates two threads in order to send and receive messages simultaneously. The client will run 
        until both threads are terminated. The connection is closed by the message_read() method.
        
        '''
        
        self.__connection.connect((host, port))
        print('Enter your username')
        
        read_thread = threading.Thread(target = self.message_read)
        read_thread.start()
        
        write_thread = threading.Thread(target = self.message_write)
        write_thread.start()
        
        
    def message_write(self):
        '''Method taking care of input from the user. Initiating and controlling the entry window'''
        
        def callback():
            '''Called by pushing the tkinter button. Set up message to the server.'''
            
            #Sends login request if you're not logged in. Terminates method when message sent.
            if not self.__login:
                self.__user = entryWindow.get()
                data = json.dumps({'user': self.__user, 
                                   'message': self.__user + ' has entered the chat', 
                                   'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                   'flag': 'login'})
                self.sendData(data)
                return
            
            #Sends disconnect request. Terminates the input loop when message sent.
            if entryWindow.get() == 'exit' or entryWindow.get() == 'quit':
                data = json.dumps({'user': self.__user, 
                                   'message': self.__user + ' has left the chat', 
                                   'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                                   'flag': 'disconnect'})
                self.sendData(data)
                root.quit()
            
            #Sends message to all connected clients.
            else:
                data = json.dumps({'user': self.__user, 
                               'message': entryWindow.get(), 
                               'time': datetime.now().strftime("%Y-%m-%d %H:%M"), 
                               'flag': 'none'})
                self.sendData(data)
        
        
        #Root to which add the tkinter components
        root = tkinter.Tk()
        
        #tkinter entry window
        entryWindow = tkinter.Entry(root)
        entryWindow.pack()
        entryWindow.focus_set()
        
        #tkinter button
        button = tkinter.Button(root, text = "OK", width = 10, command = callback)
        button.pack()
        
        #Activates the components and loops until terminated
        tkinter.mainloop()
        
            
    def message_read(self):
        '''Method taking care of output. Prints to the console window.'''
        
        #Login loop. Terminates once __login is true (username is valid)
        while not self.__login:
            data = self.receive()
            print(data['message'])
            
            #Checks message to see if login was successful 
            if not (data['message'] == 'invalid username'):
                self.__login = True
        
        #Input loop. Terminates once __login is false
        while self.__login:
            data = self.receive()
            
            #Determine if data is status notification or message
            if data['flag'] == 'disconnect' or data['flag'] == 'login':
                print(data['message']) 
            else:
                print(data['time'] + ' ' + data['user'] + ': ' + data['message'])    
            
            #Checks message to see if logout was successful   
            if data['message'] == 'now exiting...':
                self.__login = False   
        
        self.__connection.close()


    def sendData(self, data):
        '''Method for encoding and sending data'''
        
        data = data.encode('utf-8')
        self.__connection.sendall(data)


    def receive(self):
        '''Method for decoding and loading received data according to the protocol'''
        
        received_data = self.__connection.recv(1024)
        received_data = received_data.decode('utf-8')
        received_data = json.loads(received_data)
        return received_data


if __name__ == "__main__":
    '''Creates a Client object. Initiates it.
    '''
    client = Client()
    client.start('localhost', 8088)
