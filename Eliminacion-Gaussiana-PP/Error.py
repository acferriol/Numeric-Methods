class Error (Exception):
    def __init__ (self,mess):
        super().__init__(mess)
        self.mess=mess