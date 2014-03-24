
'''Classes for creating events and dispatching events to listeners.

Each event have an unique type. Events function as messages to all objects listening to events of that certain 
type. In this project we will use only one type of event, NEW, telling listening objects if a client sent a 
new message.

The event dispatcher holds functionality to store listening instances and dispatching events to the ones 
associated with the specific events type.

'''

NEW = 'newMessage' #Event type informing about new message from a client

class Event(object):
    
    '''Event object with unique type. No mutator methods, only accessor methods.'''
    
    def __init__(self, event_type):
        self.__type = event_type

 
    def getType(self):
        return self.__type




class EventDispatcher(object):
    
    '''Class for dispatching events to and storing listening instances.
    
    Methods:
    
    - __init__()
    - __del__()
    - has_listener(event_type, listener)
    - dispatch_event(event)
    - add_event_listener(event_type, listener)
    - remove_event_listener(event_type, listener)
    
    Instance variables:
    
    - __events
    
    '''
 
    def __init__(self):
        '''Constructor. Create dictionary to sort instances according to Event types.'''
        
        self.__events = dict()  #Holds listening instances
 
 
    def __del__(self):
        '''Destructor. Empty dictionary for object references.'''
        
        self.__events = None
 
 
    def has_listener(self, event_type, listener):
        '''Search for Event type and listeners. Return True if listener exists in dictionary.'''
        
        if event_type in self.__events.keys():
            return listener in self.__events[event_type]
        else:
            return False
 
 
    def dispatch_event(self, event):
        '''Dispatch event to associated listeners if Event type and listeners exist.'''
        
        if event.getType() in self.__events.keys():
            listeners = self.__events[event.getType()]
            
            for listener in listeners:
                listener()
 
 
    def add_event_listener(self, event_type, listener):
        '''#Add listener to dictionary if not a duplicate.'''
        
        if not self.has_listener(event_type, listener):
            listeners = self.__events.get(event_type, [])
            listeners.append(listener)
            self.__events[event_type] = listeners

 
    def remove_event_listener(self, event_type, listener):
        '''Remove existing listener of the Event type from the dictionary.'''

        if self.has_listener(event_type, listener):
            listeners = self.__events[event_type]
 
            #Deletes Event type key in dictionary if only associated listener remaining
            if len(listeners) == 1:
                del self.__events[event_type]
            else:
                listeners.remove(listener)
                self.__events[event_type] = listeners
                