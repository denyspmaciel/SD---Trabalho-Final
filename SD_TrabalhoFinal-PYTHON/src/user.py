#-*- coding:utf-8 -*-

import sd_times_pb2, Proxy
from __builtin__ import raw_input

class user:
    def AddTime(self):
            time = sd_times_pb2.Time()
            time.nome = raw_input("Nome:")
            time.data = raw_input("Data de Criação:")
            time.titulos = raw_input("Titulos:")
            
            teste = proxy.BuscaTime(time)
            if teste.nome != "lixo" :
                print "\n\nQueridão esse time já existe no banco\n\n"
            else:
                msg = proxy.AddTime(time)
                if  msg == "true":
                    print "Sucesso\n\n"
                else:
                    print "Algo errado não está certo\n\n"
        
    
    def List(self):
            time = sd_times_pb2.Time()
            #time.nome="busca"
            #time.data="busca"
            #time.titulos="busca"
            
            times = proxy.List(time)
            if times is not None:
                if times == '':
                    print "Estoque vazio"
                else:
                    try:
                        for time in times.time:
                            print "ID:", time.id
                            print "Nome:", time.nome
                            print "Data:", time.data
                            print "Titulos:", time.titulos
                            print "\n\n"
                    except ValueError:
                        print "\nNão foi possivel consultar o estoque\n"
            else:
                print "\n"
    
    
    def BuscaTime(self):
            busca = sd_times_pb2.Time()
            busca.nome="Vasco"
            busca.data="busca"
            busca.titulos="busca"
            
            
            time = proxy.BuscaTime(busca)
            
            print "\n\n##################################"
            print "ID: ", time.id
            print "Nome: ", time.nome
            print "Data de Criação: ", time.data
            print "Titulos: ", time.titulos
            print "\n\n"
    
    def RemoveTime(self):       
            busca = sd_times_pb2.Time()
            times = proxy.List(busca)
            try:
                print "\n###################################"
                for time in times.time:
                    print "ID:", time.id," Nome:", time.nome
                    print "###################################"
            except ValueError:
                    print "\nNão foi possivel consultar o estoque\n"

            #time que sera removido
            remove = sd_times_pb2.Time()
            remove.id = int(raw_input("Informe o ID do time: "))
            #remove.nome = "remove"
            
            menino_burro = True
            
            for time in times.time:
                if remove.id == time.id:
                    menino_burro = False
            if menino_burro:
                print "\n\nQueridão informe um ID válido\n\n"
            else:
                resp = proxy.RemoveTime(remove)
                if resp == "true":
                    print "\nTime removido com sucesso\n"
                else:
                    print "\nDeu erro\n"
        
    def AddTitulo(self):
            busca = sd_times_pb2.Time()  
            times = proxy.List(busca)
            try:
                print "\n###################################"
                for time in times.time:
                    print "ID:", time.id," Nome:", time.nome
                    print "Titulos: ", time.titulos
                    print "###################################\n"
            except ValueError:
                    print "\nNão foi possivel consultar o estoque\n"  

            ID = int(raw_input("Informe o ID do time a ser alterado: "))
            
            menino_burro = True
            
            
            for time in times.time:
                    if ID == time.id:
                        alterar = time
                        menino_burro=False
            
            if menino_burro:
                print "\n\nQueridão informe um ID válido\n\n"
            else:
                titulo = raw_input("Informe o novo Titulo: ")
                alterar.titulos = alterar.titulos + " : " + titulo            
                resp = proxy.AddTitulo(alterar)
                if resp == "true":
                    print "\nTime atualizado com sucesso\n"
                else:
                    print "\nDeu erro\n"
            

if __name__ == '__main__':
    u = user()
    proxy = Proxy.ProxyUser()
    op = -1
    while op!=6:
        print "\n##################"
        print "Escolha a opção desejada"
        print "1 - Adicionar Time"
        print "2 - Listar Times"
        print "3 - Remover Time"
        print "4 - Buscar Time"
        print "5 - Adicionar Titulo"
        print "6 - Sair"
        print "##################\n"
        op = int(raw_input("Digite a opção: "))
        
        if op == 1:
            u.AddTime()
        elif op == 2:
            u.List()
        elif op == 3:
            u.RemoveTime()
        elif op == 4:
            u.BuscaTime()
        elif op == 5:
            u.AddTitulo()
        else:
            print "Queridão digite uma opção válida" 
            
      
    print "Terminou"
        
        
        
        
        