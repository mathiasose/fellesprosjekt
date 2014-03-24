
import event

class Backlog(object):
    
    def __init__(self, event_dispatcher):
        self.__event_dispatcher = event_dispatcher
        self.__memory = []
        
    def getMsgs(self):
        return self.__memory
    
    def getMsg(self, i):
        return self.__memory[i]
    
    def saveMsg(self, msg):
        self.__memory.append(msg)
        self.__event_dispatcher.dispatch_event(event.Event(event.NEW))


class Userlist(object):
    
    def __init__(self):
        self.__memory = []
        
    def getUsers(self):
        return self.__memory 
    
    def saveUser(self, user):
        self.__memory.append(user)