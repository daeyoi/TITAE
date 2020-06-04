class User :
    def __init__(self, host, user,passwd,dbname) :
        self.host = host
        self.user = user
        self.passwd = passwd
        self.dbname = dbname

    def get_host(self) :
        return self.host
    def get_user(self) :
        return self.user
    def get_passwd(self) :
        return self.passwd
    def get_dbname(self) :
        return self.dbname
