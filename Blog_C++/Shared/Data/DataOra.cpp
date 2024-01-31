#include "DataOra.h"


void DataOra::set_zi(int a){
    this->zi=a;
}
void DataOra::set_luna(int b){
    this->luna=b;
}
void DataOra::set_an(int c){
    this->an=c;
}
void DataOra::set_ora(int d){
    this->ora=d;
}
void DataOra::set_minut(int e){
    this->minut=e;
}

int DataOra::get_zi()
{
    return zi;
}

int DataOra::get_luna()
{
    return luna;
}

int DataOra::get_an()
{
    return an;
}

int DataOra::get_ora()
{
    return ora;
}

int DataOra::get_minut()
{
    return minut;
}
ostream& operator<<(ostream& st, const DataOra& p)
{
    st << p.zi << " " << p.luna << " " << p.an<<" ";
    st <<  p.ora << " " << p.minut<<" ";
    return st;
}
