'''
Created on 7. mars 2014

@author: Fredrik
'''

import event

class EventDispatcher( object ):
 
    def __init__(self):
        self._events = dict()
 
 
    def __del__(self):
        self._events = None
 
 
    def has_listener(self, event_type, listener):
        
        if event_type in self._events.keys():
            return listener in self._events[event_type]
        
        else:
            return False
 
 
    def dispatch_event(self, evt):
        
        if evt.getType() in self._events.keys():
            listeners = self._events[evt.getType()]
            
            for listener in listeners:
                listener()
 
 
    def add_event_listener(self, event_type, listener):
        
        if not self.has_listener(event_type, listener):
            listeners = self._events.get(event_type, [])

            listeners.append(listener)
 
            self._events[event_type] = listeners

 
    def remove_event_listener(self, event_type, listener):

        if self.has_listener(event_type, listener):
            listeners = self._events[event_type]
 
            if len(listeners) == 1:
                del self._events[event_type]
 
            else:
                listeners.remove(listener)
 
                self._events[event_type] = listeners
                