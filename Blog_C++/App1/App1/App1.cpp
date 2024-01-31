#include <iostream>
#include <fstream>
#include <string.h>
#include <string>
#include <vector>
#include <cstdlib>
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/Articol.h"
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/Articol.cpp"
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/DataOra.h"
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/DataOra.cpp"
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/Comentariu.cpp"
#include "/Users/Admin/Desktop/Facultate/Materie/PP/Proiect C++/Shared/Data/Comentariu.h"

using namespace std;

vector <Articol> articole;


void citire_fisier()
{
    articole.clear();
    ifstream fin("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\articole.txt");
    ifstream f("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\comentarii.txt");
    if (fin.eof())
        return;
    int n;
    fin >> n;
    for (int i = 0; i < n; i++)
    {
        fin.ignore();
        string titlu, autor, continut;
        int  zi, luna, an, minut, ora, like, dislike, love;
        getline(fin, titlu);
        getline(fin, autor);

        //autor.erase(autor.begin());
        getline(fin, continut);
        //continut.erase(continut.begin());
        fin >> zi >> luna >> an >> ora >> minut;
        fin >> like >> love >> dislike ;
        DataOra p;
        p.set_an(an);
        p.set_luna(luna);
        p.set_zi(zi);
        p.set_ora(ora);
        p.set_minut(minut);
        Articol art;
        art.set_autor(autor);
        art.set_continut(continut);
        art.set_titlu(titlu);
        art.set_timp(p);
        art.set_dislike(dislike);
        art.set_like(like);
        art.set_love(love);
        articole.push_back(art);
    }
    fin.close();
    int nr;
    f >> nr;
    f.ignore();
    for (int i = 0; i < nr; i++)
    {
        string titlu, autor, continut;
        Comentariu com;
        getline(f, titlu);
        getline(f, autor);
        getline(f, continut);
        com.set_titlu(titlu);
        com.set_autor(autor);
        com.set_continut(continut);
        int cnt = -1;
        for (int j = 0; j < n; j++)
            if (articole[j].get_titlu() == titlu)
            {
                cnt = j;
                break;
            }
        if(cnt!=-1)
        articole[cnt].adaugare_comentariu(com);
    }
    f.close();
}


void afisare_fisier()
{
    ofstream fout("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\articole.txt");
    fout << articole.size()<<endl;
    for (auto i = articole.begin(); i != articole.end(); i++)
        fout << *i;
    fout.close();
}

void vizualizare_articole()
{
    for (int i = 0; i < articole.size(); i++)
    {
      
        cout << "Titlu: "<<articole[i].get_titlu() << endl << "Autor: " << articole[i].get_autor() << endl << "Continut: " << articole[i].get_continut()<<endl;
        cout << "Data: " << articole[i].get_timp().get_zi() << "-" << articole[i].get_timp().get_luna() << "-" << articole[i].get_timp().get_an() << "  Ora: " << articole[i].get_timp().get_ora() << ":" << articole[i].get_timp().get_minut() << endl;
        if (articole[i].get_comentarii().empty())
            cout << "Nu exista comentarii pentru acest articol" << endl;
        else
        {
            cout << "Lista comentarii: " << endl;
            for (auto k : articole[i].get_comentarii())
                cout << k.get_autor() << " :" << k.get_continut()<<endl;
        }
        cout << "Like-uri: " << articole[i].get_like() << endl << "Love-uri: " << articole[i].get_love() << endl << "Dislike-uri: " << articole[i].get_dislike() << endl;
        cout << endl;
    }

}

void adaugare_articol(char *a, char *b, char *c, char *d, char *e, char *f, char *g, char *h)
{
    string titlu = a;
    string autor = b;
    string continut = c;
    int n = atoi(d);
    int m = atoi(e);
    int p1 = atoi(f);
    int q = atoi(g);
    int r = atoi(h);
    DataOra p;
    p.set_an(p1);
    p.set_luna(m);
    p.set_zi(n);
    p.set_ora(q);
    p.set_minut(r);
    Articol art;
    art.set_autor(autor);
    art.set_continut(continut);
    art.set_titlu(titlu);
    art.set_timp(p);
    art.set_dislike(0);
    art.set_like(0);
    art.set_love(0);
    articole.push_back(art);
    afisare_fisier();
}

void stergere_articol(char* a)
{
    string titlu = a;
    bool da=0;
    int cont;
    for (int i=0;i<articole.size();i++)
        if (articole[i].get_titlu() == titlu)
        {
            cont = i;
            da = 1;
            break;
        }
    if (da == 0)
        cout << "Nu exista acest articol";
    else
    {
        articole.erase(articole.begin() + cont);
        afisare_fisier();
    }
        
}

int main(int arg, char** argv)
{   
    citire_fisier();
    if (strcmp(argv[1], "vizualizare_articole") == 0) {
        if (arg != 2)
        {
            cout <<"Sintaxa invalida! Sintaxa corecta: ./App1.exe vizualizare_articole";
        }
        else
            vizualizare_articole();//sa fac continutul sa fie un string cu spatii
    }
    else
    if (strcmp(argv[1], "postare_articol") == 0) {
        if (arg != 10)
        {
            cout << "Sintaxa invalida! Sintaxa corecta: ./App1.exe postare_articol <titlu> <autor> <continut> <zi> <luna> <an> <ora> <minut>";
        }
        else
            adaugare_articol(argv[2], argv[3], argv[4], argv[5], argv[6], argv[7], argv[8], argv[9]);
       
    }
    else
    if (strcmp(argv[1], "stergere_articol") == 0) {
        if (arg != 3)
        {
            cout << "Sintaxa invalida! Sintaxa corecta: ./App1.exe stergere_articol <titlu>";
        }
        else
            stergere_articol(argv[2]);

    }
    else
        cout << "COMANDA NU ESTE VALIDA!";
}

