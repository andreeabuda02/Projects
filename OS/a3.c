#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <sys/mman.h>

#define RESP_PIPE_NAME "RESP_PIPE_81948"
#define REQ_PIPE_NAME "REQ_PIPE_81948"

int main()
{
    int resp_pipe, req_pipe;
    char buffer[256];

    //creez pipe-ul raspuns
    if (mkfifo(RESP_PIPE_NAME, 0666) != 0)
    {
        perror("ERROR\ncannot create the response pipe");
        exit(1);
    }

    //deschid pipe-ul de cerere in mod citire
    req_pipe = open(REQ_PIPE_NAME, O_RDONLY);
    if (req_pipe == -1)
    {
        perror("ERROR\ncannot open the request pipe");
        exit(1);
    }

    //deschid pipe-ul de raspuns in mod scriere
    resp_pipe = open(RESP_PIPE_NAME, O_WRONLY);
    if (resp_pipe == -1)
    {
        perror("ERROR\ncannot open the response pipe");
        exit(1);
    }

    //scrie mesajul de cerere in pipe-ul raspuns
    const char* request_message = "HELLO";
    const char* message_rsp = "PONG";
    unsigned regiune;
    unsigned shmFd;//locatia unde se afla memoria partajata
    volatile char* shared_char;


    char size=5;
    write(resp_pipe, &size, 1);
    write(resp_pipe, request_message, size);
    printf("SUCCESS\n");

    //citirea si tratarea cererilor
    while (1)
    {
        //citeste mesajul de cerere din pipe-ul de cerere
        read(req_pipe, &size, 1);
        read(req_pipe, buffer, size);
        buffer[(int)size]='\0';
        if(strcmp(buffer,"PING")==0)
        {
            write(resp_pipe, &size, 1);
            write(resp_pipe, buffer, size);

            unsigned nr=81948;
            write(resp_pipe, &nr, size);

            write(resp_pipe, &size, 1);
            write(resp_pipe, message_rsp, size);

        }
        else
        {
            if(strcmp(buffer,"CREATE_SHM")==0)
            {
                read(req_pipe, &regiune, 4);
                shmFd=shm_open("/0ScV4O",O_CREAT|O_RDWR, 0664);
                if(shmFd<0)
                {
                    char size_shm=10;
                    const char* message_shm = "CREATE_SHM";
                    const char* err_shm = "ERROR";
                    write(resp_pipe, &size_shm, 1);
                    write(resp_pipe, message_shm, size_shm);
                    size_shm=5;
                    write(resp_pipe, &size_shm, 1);
                    write(resp_pipe, err_shm, size_shm);

                }
                else
                {
                    ftruncate(shmFd,sizeof(char));
                    shared_char = (volatile char*)mmap(0,sizeof(char),PROT_READ|PROT_WRITE, MAP_SHARED, shmFd, 0);
                    if(shared_char==(void*)-1)
                    {
                        char size_shm=10;
                        const char* message_shm = "CREATE_SHM";
                        const char* err_shm = "ERROR";
                        write(resp_pipe, &size_shm, 1);
                        write(resp_pipe, message_shm, size_shm);
                        size_shm=5;
                        write(resp_pipe, &size_shm, 1);
                        write(resp_pipe, err_shm, size_shm);
                    }
                    else
                    {
                        char size_shm=10;
                        const char* message_shm = "CREATE_SHM";
                        const char* succ_shm = "SUCCESS";
                        write(resp_pipe, &size_shm, 1);
                        write(resp_pipe, message_shm, size_shm);
                        size_shm=7;
                        write(resp_pipe, &size_shm, 1);
                        write(resp_pipe, succ_shm, size_shm);
                    }
                }

            }
            else
            {
                if(strcmp(buffer,"MAP_FILE")==0)
                {
                    //citirea numelui fisierului
                    read(req_pipe, &size,1);
                    read(req_pipe,buffer,size);
                    buffer[(int)size]='\0';
                    const char* nume_aux=buffer;

                    //maparea fisierului in memorie
                    int fd_aux=open(nume_aux, O_RDONLY);
                    if(fd_aux==-1)
                    {
                        char size_rsp=8;
                        const char* mesaj_rsp="MAP_FILE";
                        const char* eroare_rsp="ERROR";

                        write(resp_pipe,&size_rsp,1);
                        write(resp_pipe,mesaj_rsp,size_rsp);
                        size_rsp=5;
                        write(resp_pipe,&size_rsp,1);
                        write(resp_pipe,eroare_rsp,size_rsp);

                    }
                    else
                    {
                        off_t file_size=lseek(fd_aux,0,SEEK_END);
                        if(file_size==-1)
                        {
                            char size_rsp=8;
                            const char* mesaj_rsp="MAP_FILE";
                            const char* eroare_rsp="ERROR";

                            write(resp_pipe,&size_rsp,1);
                            write(resp_pipe,mesaj_rsp,size_rsp);
                            size_rsp=5;
                            write(resp_pipe,&size_rsp,1);
                            write(resp_pipe,eroare_rsp,size_rsp);

                        }
                        else
                        {
                            void *fisier_mapat=mmap(NULL,file_size,PROT_READ,MAP_SHARED,fd_aux,0);
                            if(fisier_mapat==MAP_FAILED)
                            {
                                char size_rsp=8;
                                const char* mesaj_rsp="MAP_FILE";
                                const char* eroare_rsp="ERROR";

                                write(resp_pipe,&size_rsp,1);
                                write(resp_pipe,mesaj_rsp,size_rsp);
                                size_rsp=5;
                                write(resp_pipe,&size_rsp,1);
                                write(resp_pipe,eroare_rsp,size_rsp);
                            }
                            else
                            {
                                char size_rsp=8;
                                const char* mesaj_rsp="MAP_FILE";
                                const char* succes_rsp="SUCCESS";

                                write(resp_pipe,&size_rsp,1);
                                write(resp_pipe,mesaj_rsp,size_rsp);
                                size_rsp=7;
                                write(resp_pipe,&size_rsp,1);
                                write(resp_pipe,succes_rsp,size_rsp);
                            }
                            close(fd_aux);
                        }
                    }

                }
                else{
                    break;
                }

            }
        }
    }

    //inchid pipe-urile
    close(req_pipe);
    close(resp_pipe);

    //sterg pipe-ul raspuns
    if (unlink(RESP_PIPE_NAME) == -1)
    {
        exit(1);
    }

    return 0;
}
