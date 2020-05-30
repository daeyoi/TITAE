class Product :
    def __init__(self,bankname, productname, rate, description) :
        self.bankname = bankname
        self.productname = productname
        self.rate = rate
        self.description = description

    def get_bankname(self) :
        return self.bankname
    def get_productname(self) :
        return self.productname
    def get_rate(self) :
        return self.rate
    def get_description(self) :
        return self.description


    def set_bankname(self,bankname) :
        self.bankname = bankname
        
    def set_productname(self,productname) :
        self.productname = productname
        
    def set_rate(self,rate) :
        self.rate = rate
        
    def set_description(self,description) :
        self.description = description
