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
vector <Comentariu> comentarii;

void citire_fisier()
{
    articole.clear();
    ifstream fin("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\articole.txt");
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
        fin >> like >> dislike >> love;
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
}
void citire_fisier_com()
{
    comentarii.clear();
    ifstream fin("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\comentarii.txt");
    if (fin.eof())
        return;
    if (fin.is_open()) {
        string autor, continut,titlu;
        int n;
        fin >> n;
        fin.ignore();
        while (getline(fin,titlu)&&getline(fin, autor) && getline(fin, continut)) {
            Comentariu comentariu;
            comentariu.set_titlu(titlu);
            comentariu.set_autor(autor);
            comentariu.set_continut(continut);
            comentarii.push_back(comentariu);
        }

        fin.close();
    }
    else {
        cout << "Eroare la deschiderea fisierului." << endl;
    }
    fin.close();
    
}

void afisare_fisier()
{
    ofstream fout("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\articole.txt");
    fout << articole.size() << endl;
    for (auto i = articole.begin(); i != articole.end(); i++)
        fout << *i;
    fout.close();
}

void afisare_fisier_com()
{
    ofstream fout("C:\\Users\\Admin\\Desktop\\Facultate\\Materie\\PP\\Proiect C++\\Shared\\Files\\comentarii.txt");
    fout << comentarii.size() << endl;
    if (fout.is_open()) {
        for (auto comentariu : comentarii) {
            fout << comentariu.get_titlu() << endl;
            fout << comentariu.get_autor() << endl;
            fout << comentariu.get_continut() << endl;
        }
        fout.close();
        cout << "Comentariile au fost salvate cu succes in fisierul comentarii.txt." << endl;
    }
    else {
        cout << "Eroare la deschiderea fisierului comentarii.txt." << endl;
    }
}


void adaugare_comentariu( char* titlu,  char*autor ,char* comentariu)
{
    string titluArticol = titlu;
    string continutComentariu = comentariu;
    string autor1 = autor;
    Comentariu comentariuObj;
    int cont = -1;
    for(int i=0;i<articole.size();i++)
        if(titluArticol==articole[i].get_titlu())
        {
            cont = i;
            break;
        }
    if (cont != -1) {
        comentariuObj.set_titlu(titlu);
        comentariuObj.set_continut(continutComentariu);
        comentariuObj.set_autor(autor1);
        comentarii.push_back(comentariuObj);
        afisare_fisier_com();
    }
    else
        cout << "Titlul introdus nu exista";
}

void adaugare_interactiune(char* a, char *b)
{

    string titlu = a;
    string interactiune = b;
    int cont = -1;
    for (int i = 0; i < articole.size(); i++)
        if (titlu == articole[i].get_titlu())
        {
            cont = i;
            break;
        }
    if (cont != -1) {
        if (interactiune == "love")
            articole[cont].adaugare_love();
        else
            if (interactiune == "like")
                articole[cont].adaugare_like();
            else
                if (interactiune == "dislike")
                    articole[cont].adaugare_dislike();
                else
                    cout << "Interactiunea introdusa nu exista";
        afisare_fisier();
    }
    else
        cout << "Titlul introdus nu exista";

}

int main(int arg, char** argv)
{
    citire_fisier();
    citire_fisier_com();
    if (strcmp(argv[1], "adaugare_comentariu") == 0) {
        if (arg != 5)
        {
            cout << "Sintaxa invalida! Sintaxa corecta: ./App_2.exe adaugare_comentariu <titlu_articol> <autor_comentariu> <comentraiu>";
        }
        else
            adaugare_comentariu(argv[2], argv[3], argv[4]);
    }
    else
        if (strcmp(argv[1], "adaugare_interactiune") == 0) {
            if (arg != 4)
            {
                cout << "Sintaxa invalida! Sintaxa corecta: ./App2.exe adaugare_interactiune <titlu_articol> <tip_interactiune>";
            }
            else
                adaugare_interactiune(argv[2], argv[3]);

        }
        else
           
      cout << "COMANDA NU ESTE VALIDA!";
}

