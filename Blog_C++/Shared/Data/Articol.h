#ifndef ARTICOL_H
#define ARTICOL_H
#include <list> //sau vector
#include "DataOra.h"
#include <string>
#include "Comentariu.h"
using namespace std;

class Articol
{
private:
    DataOra timp;
    string titlu, autor, continut;
    list <Comentariu> comentarii;  // sau putem pune vector
    int love, like, dislike;
public:
    Articol() { love = 0; like = 0; dislike = 0; }
    void set_timp(DataOra);
    void set_titlu(string);
    void set_autor(string);
    void set_continut(string);
    void set_comentarii(list <Comentariu>);
    void set_love(int);
    void set_like(int);
    void set_dislike(int);
    DataOra get_timp();
    string get_titlu();
    string get_autor();
    string get_continut();
    list <Comentariu> get_comentarii();
    void adaugare_comentariu(const Comentariu&);
    int get_love();
    int get_like();
    int get_dislike();
    void adaugare_love();
    void adaugare_like();
    void adaugare_dislike();
    friend ostream & operator << (ostream&, const Articol&);
    virtual ~Articol(){};
};



#endif // ARTICOL_H
