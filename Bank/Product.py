class Product :
    def __init__(self,bankname, productname, rate, description,target ,calmethod ,reservingmethod) :
        self.bankname = bankname
        self.productname = productname
        self.rate = rate
        self.description = description
        self.target = target
        self.calmethod = calmethod
        self.reservingmethod = reservingmethod


    def get_bankname(self) :
        return self.bankname
    def get_productname(self) :
        return self.productname
    def get_rate(self) :
        return self.rate
    def get_description(self) :
        return self.description
    def get_target(self) :
        return self.target
    def get_calmethod(self) :
        return self.calmethod
    def get_reservingmethod(self) :
        return self.reservingmethod



    def set_bankname(self,bankname) :
        self.bankname = bankname
        
    def set_productname(self,productname) :
        self.productname = productname
        
    def set_rate(self,rate) :
        self.rate = rate
        
    def set_description(self,description) :
        self.description = description
        
    def set_target(self, target) :
        self.target = target
        
    def set_calmethod(self, calmethod) :
        self.calmethod = calmethod
        
    def set_reservingmethod(self, reservingmethod) :
        self.reservingmethod = reservingmethod

