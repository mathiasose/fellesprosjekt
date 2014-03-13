'''
Created on 7. mars 2014

@author: Fredrik
'''


NEW = 'newMessage'

class Event( object ):
    
    def __init__(self, event_type, data=None):
        self._type = event_type
        self._data = data

 
    def getType(self):
        return self._type
 

    def getData(self):
        return self._data