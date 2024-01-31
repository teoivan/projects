#ifndef DATAORA_H
#define DATAORA_H
#include <iostream>

using namespace std;

class DataOra
{
private:
    int zi,luna,an;
    int ora, minut;
public:
    DataOra() { zi = 0; luna = 0; an = 0; ora = 0; minut = 0; };
    void set_zi(int);
    void set_luna(int);
    void set_an(int);
    void set_ora(int);
    void set_minut(int);
    int get_zi();
    int get_luna();
    int get_an();
    int get_ora();
    int get_minut();
    friend ostream & operator << (ostream& , const DataOra& );

};



#endif // DATAORA_H
