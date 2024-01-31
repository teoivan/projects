#include "Articol.h"

void Articol::set_timp(DataOra a){
    this->timp=a;
};
void Articol::set_titlu(string b){
    this->titlu=b;
};
void Articol::set_autor(string c){
    this->autor=c;
};
void Articol::set_continut(string d){
    this->continut=d;
};
void Articol::set_comentarii(list <Comentariu> e){
    this->comentarii=e;
};
void Articol::set_love(int f){
    this->love=f;
};
void Articol::set_like(int g){
    this->like=g;
};
void Articol::set_dislike(int h){
    this->dislike=h;
};

DataOra Articol::get_timp()
{
    return timp;
}
string Articol::get_titlu()
{
    return titlu;
}
string Articol::get_autor()
{
    return autor;
}
string Articol::get_continut()
{
    return continut;
}
list <Comentariu> Articol:: get_comentarii()
{
    return comentarii;
}
int  Articol::get_love()
{
    return love;
}
int Articol::get_like()
{
    return like;
}
int Articol::get_dislike()
{
    return dislike;
}
void  Articol::adaugare_love()
{
    this->love++;
}
void  Articol::adaugare_like()
{
    this->like++;
}
void  Articol::adaugare_dislike()
{
    this->dislike++;
}
ostream & operator << (ostream& st, const Articol& p)
{
    st  << p.titlu << endl<< p.autor << endl << p.continut<<endl<<p.timp;
    st <<p.like<<" "<<p.love<<" "<<p.dislike<<endl;
    return st;
}
void Articol::adaugare_comentariu(const Comentariu& p)
{
    comentarii.push_back(p);
}
