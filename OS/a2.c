#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>
#include <fcntl.h>

#include "a2_helper.h"

sem_t *semafor_p5_t3;
sem_t *semafor_p5_t4;

sem_t *semafor_p5_t2;
sem_t *semafor_p4_t4;


/* Structura prin care definesc parametrii pentru thread-urile utilizate in functia "blocare_threaduri". */
typedef struct
{
    int id;    //numarul thread-ului
    int nr_proces;  //numarul procesului la care apartine threadul
    sem_t *semafor_p2; //pointer catre semaforul utilizat pentru blocarea si sincronizarea thread-urilor
} thread_struct;

/* Structura prin care definesc parametrii pentru thread-urile utilizate in functia "sincronizare_th_procese_diferite" */
typedef struct
{
    int id;  //numarul thread-ului
    int nr_proces;  //numarul procesului la care apartine threadul
} thread_struct1;

/* Functie pentru threadul1 din procesul 5. Acesta incepe si se incheie si afiseaza mesaje de inceput si final utilizand functia "info".*/
void *thread_function5_1(void* arg)
{
    info(BEGIN, 5, 1);
    info(END, 5, 1);
    return NULL;
}

/* Functie pentru threadul 2 din procesul 5. Acesta asteapta semnalul de la semaforul "semafor_p5_t2",
 afiseaza un mesaj de intrare si unul de iesire si elibereaza semaforul "semafor_p4_t4".*/
void *thread_function5_2(void* arg)
{
    sem_wait(semafor_p5_t2); // thread-ul 2 din procesul 5 asteapta sa se termine thread-ul 5 din procesul 4

    info(BEGIN, 5, 2);
    info(END, 5, 2);

    sem_post(semafor_p4_t4); //trezeste thread-ul 4 din procesul 4
    return NULL;
}

/* Functie pentru threadul 3 din procesul 5. Acesta asteapta semnalul de la semaforul "semafor_p5_t3", afiseaza un mesaj de intrare si unul de iesire
si elibereaza semaforul "semafor_p5_t4".*/
void *thread_function5_3(void* arg)
{
    sem_wait(semafor_p5_t3); // thread-ul 3 din procesul 5 asteapta sa inceapa thread-ul 4 din procesul 5

    info(BEGIN, 5, 3);
    info(END, 5, 3);

    sem_post(semafor_p5_t4);  //trezeste thread-ul 4 din procesul 5
    return NULL;
}

/* Functie pentru threadul 4 din procesul 5. Acesta incepe, apoi elibereaza semaforul "semafor_p5_t3" si asteapta semnalul de la "semafor_p5_t4".
Dupa primirea semnalului se incheie.*/
void *thread_function5_4(void* arg)
{
    info(BEGIN, 5, 4);

    sem_post(semafor_p5_t3); //trezeste thread-ul 3 din procesul 5
    sem_wait(semafor_p5_t4);  // thread-ul 4 din procesul 5 asteapta sa se termine thread-ul 3 din procesul 5

    info(END, 5, 4);
    return NULL;
}

/* Functie care primeste un argument de tip void* pe care il converteste intr-o structura de tip "thread_struct". Apoi asteapta semnalul de la
"semafor_p2" apoi afiseaza mesajele de inceput si final si elibereaza semaforul "semafor_p2". Functia permite sa fie active simultan 4 thread-uri.*/
void *blocare_threaduri(void *arg)
{
    thread_struct *sem=(thread_struct*) arg;

    sem_wait(sem->semafor_p2);

    info(BEGIN, sem->nr_proces, sem->id);
    info(END, sem->nr_proces, sem->id);

    sem_post(sem->semafor_p2);
    return(NULL);
}

/* Functie care primeste un argument de tip void* pe care il converteste intr-o structura de tip "thread_struct1". Apoi asteapta semnalul de la
"semafor_p4_t4" insa doar in cazul firului de executie 4 din procesul 4, apoi afiseaza mesajele de inceput si final si elibereaza semaforul "semafor_p5_t2", in cazul
firului de executie 5 din procesul 4. */
void *sincronizare_th_procese_diferite(void *arg)
{
    thread_struct1 *sem=(thread_struct1*) arg;

    if(sem->nr_proces==4 && sem->id==4)
    {
        sem_wait(semafor_p4_t4);
    }

    info(BEGIN, sem->nr_proces, sem->id);
    info(END, sem->nr_proces, sem->id);

    if(sem->nr_proces==4 && sem->id==5)
    {
        sem_post(semafor_p5_t2);
    }
    return(NULL);
}

/*
Pentru prima cerinta: a inceput procesul 1,  a inceput procesul 2, a inceput procesul 5, am terminat procesul 5, a inceput procesul 6, a inceput procesul 8,
s-a terminat procesul 8, s-a terminat procesul 6, s-a terminat procesul 2, a inceput procesul 3, s-a incheiat procesul 3, a inceput procesul 4, a inceput procesul 7,
s-a incheiat procesul 7, s-a incheiat procesul 4 si s-a incheiat procesul 1.

*/

int main(int argc, char **argv)
{
    init();

    //in cazul in care exista, sterge semafoarele precizate cu numele trasmis ca parametru

    sem_unlink("sem_p5_t4");
    sem_unlink("sem_p5_t3");

    sem_unlink("sem_p4_t4");
    sem_unlink("sem_p5_t2");

    //creez si deschid mai multe semafoare cu "sem_open" dand permisiuni de acces 0644 si le initializez cu valoarea 0

    semafor_p5_t4=sem_open("sem_p5_t4", O_CREAT, 0644, 0);
    semafor_p5_t3=sem_open("sem_p5_t3", O_CREAT, 0644, 0);

    semafor_p4_t4=sem_open("sem_p4_t4", O_CREAT, 0644, 0);
    semafor_p5_t2=sem_open("sem_p5_t2", O_CREAT, 0644, 0);

    info(BEGIN, 1, 0);

    pthread_t threaduri_p[4];

    pthread_t threaduri[39];
    thread_struct parametrii[39];

    pthread_t threaduri_dif[7];
    thread_struct1 parametrii_dif[7];

    int pid2=fork();
    if(pid2==0)
    {
        info(BEGIN, 2, 0);
        int pid5=fork();
        if(pid5==0)
        {
            //pentru sincronizarea thread-urilor din acelasi proces(procesul 5)
            //firele de executie cu pozitiile 0,2 si 3 sunt create pentru procesul 5 iar celelalte sunt create in functiile "blocare_threaduri" si "sincronizare_th_procese_diferite"
            info(BEGIN, 5, 0);
            pthread_create(&threaduri_p[0], NULL, thread_function5_1, NULL);
            pthread_create(&threaduri_p[2], NULL, thread_function5_3, NULL);
            pthread_create(&threaduri_p[3], NULL, thread_function5_4, NULL);

            for(int i=0; i<4; i++)
            {
                if(i != 1)
                    pthread_join(threaduri_p[i], NULL);
            }

            exit(5);
        }
        else
        {
            waitpid(pid5, 0, 0);
            int pid6=fork();
            if(pid6==0)
            {
                info(BEGIN, 6, 0);
                int pid8=fork();
                if(pid8==0)
                {
                    info(BEGIN, 8, 0);
                    info(END, 8, 0);
                    exit(8);
                }
                else
                {
                    waitpid(pid8, 0, 0);
                    info(END, 6, 0);
                    exit(6);
                }
            }
            else
            {
                waitpid(pid6, 0, 0);

                sem_t semafor;
                sem_init(&semafor,0,4);

                for(int i=1; i<=38; i++)
                {
                    parametrii[i].id=i;
                    parametrii[i].nr_proces=2;
                    parametrii[i].semafor_p2=&semafor;
                    pthread_create(&threaduri[i],NULL,blocare_threaduri,&parametrii[i]);
                }

                for(int i=1; i<=38; i++)
                {
                    pthread_join(threaduri[i],NULL);
                }


                sem_destroy(&semafor);
                exit(2);
            }
        }
    }
    else
    {
        waitpid(pid2, 0, 0);
        int pid3=fork();
        if(pid3==0)
        {
            info(BEGIN, 3, 0);
            info(END, 3, 0);
            exit(3);
        }
        else
        {
            waitpid(pid3, 0, 0);
            int pid4=fork();
            if(pid4==0)
            {
                info(BEGIN, 4, 0);
                int pid7=fork();
                if(pid7==0)
                {
                    info(BEGIN, 7, 0);
                    info(END, 7, 0);
                    exit(7);
                }
                else
                {
                    waitpid(pid7, 0, 0);

                    for(int i=1; i<=6; i++)
                    {
                        parametrii_dif[i].id=i;
                        parametrii_dif[i].nr_proces=4;
                        pthread_create(&threaduri_dif[i],NULL,sincronizare_th_procese_diferite,&parametrii_dif[i]);
                    }

                    //am creat aici al 2-lea thread din procesul 5 deoarece intra in deadlock (nu mai ajunge sa creeze si procesul 4)
                    pthread_create(&threaduri_p[1], NULL, thread_function5_2, NULL);

                    for(int i=1; i<=6; i++)
                    {
                        pthread_join(threaduri_dif[i],NULL);
                    }
                    pthread_join(threaduri_p[1], NULL);
                    info(END, 5, 0);
                    info(END, 2, 0);



                    info(END, 4, 0);
                    exit(4);
                }
            }
            else
            {
                waitpid(pid4, 0, 0);
                info(END, 1, 0);
                exit(1);
            }
        }
    }

    return 0;
}
