package com.java;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book
{
    int bookid;
    String title;
    String author;
    String studentname;
    boolean avaliabilty=true;
    LocalDate duedate;
    public Book(int bookid,String title,String author)
    {
        this.bookid=bookid;
        this.title=title;
        this.author=author;
    }
    double fine()
    {
        long latedays= ChronoUnit.DAYS.between(duedate,LocalDate.now());
        if(latedays>0)
            return (latedays*5);
        else
            return 0;
    }
    public String toString()
    {
        String status;
        if(avaliabilty)
            status="Available";
        else
            status="Issued to "+studentname;
        return "[" + bookid +"] "+ title+" by "+author+"-"+status;
    }
}
class Library
{
    ArrayList<Book> books=new ArrayList<>();
    Map<String,Book> issuedbooks=new HashMap<>();
    Scanner sc=new Scanner(System.in);
    double fine;
    //method to add book to library
    void addbook()
    {
        System.out.println("ID,Title,Author");
        String s[]=sc.nextLine().split(",");
        books.add(new Book(Integer.parseInt(s[0].trim()),s[1].trim(), s[2].trim()));
        System.out.println("Adding book successful");
        System.out.println("----------------------------------");
    }
    Book findbook(String id)
    {
        for(Book b:books)
        {
            if(String.valueOf(b.bookid).equals(id))
            {
                return b;
            }
        }
        return null;
    }
    //method to issue book
    void issuebook()
    {
        System.out.println("Enter Book ID: ");
        String bid=sc.nextLine().trim();
        Book b=findbook(bid);
        if(b==null)
            System.out.println("Not Found");
        else if(!b.avaliabilty)
            System.out.println("Book already issued");
        else
        {
            System.out.println("Enter student name: ");
            b.studentname=sc.nextLine();
            b.avaliabilty=false;
            b.duedate=LocalDate.now().plusDays(14);
            issuedbooks.put(bid,b);
            System.out.println("Book issued succesfully");
        }
        System.out.println("----------------------------------");
    }
    //method to return book
    void returnbook()
    {
        System.out.println("Enter Book ID: ");
        Book b=issuedbooks.remove(sc.nextLine().trim());
        if(b==null)
            System.out.println("This book is not issued");
        else
        {
            System.out.println("Fine: "+b.fine());
            fine+=b.fine();
            b.avaliabilty=true;
            b.studentname=null;
            System.out.println("Book return successful");
        }
        System.out.println("-----------------------------------");
    }
    //method to search book by name
    void searchbook()
    {
        System.out.println("Enter book name: ");
        String bname=sc.nextLine().trim().toLowerCase();
        boolean found=false;
        for(Book b:books)
        {
            if(b.title.toLowerCase().contains(bname))
            {
                System.out.println(b);
                found=true;
            }
        }
        if(!found)
            System.out.println("Book not found");
        System.out.println("----------------------------------");
    }
    //method to check avaliability of book
    void checkavaliability()
    {
        System.out.println("Enter Book ID: ");
        Book check=findbook(sc.nextLine().trim());
        if(check==null)
            System.out.println("Book not found");
        else
        {
            System.out.println(check);
        }
        System.out.println("---------------------------------------");
    }
    //method to check fine against a book
    void collectedfine()
    {
        System.out.println("Enter book ID");
        String id=sc.nextLine().trim();
        Book b1=issuedbooks.get(id);
        if(b1==null)
            System.out.println("No fines found");
        else
            System.out.println("Fine "+b1.fine());
        System.out.println("-----------------------------------");
    }
    //method to check total fine colleted
    void totalfine()
    {
        System.out.println("Total fine collected: "+fine);
        System.out.println("------------------------------");
    }
    //method to generate report
    void generatereport()
    {
        System.out.println("Totalbooks: "+books.size()+" | "+"Books issued: "+issuedbooks.size());
        for(Book b:issuedbooks.values())
            System.out.println(b.title+"-"+b.studentname+" Fine:"+b.fine());
        System.out.println("------------------------------------------------");
    }
    //method to print remainders
    void duereminder()
    {
        boolean due=false;
        for(Book b1:issuedbooks.values())
        {
            long days=ChronoUnit.DAYS.between(LocalDate.now(),b1.duedate);
            if(days<0)
            {
                System.out.println(b1.title + " is overdue by " + days + " days");
                due=true;
            }
            else if(days<=3)
            {
                System.out.println(b1.title+" due in "+days+" days ");
                due=true;
            }
        }
        if(!due)
        {
            System.out.println("No books are due in less than 3 days");
        }

        System.out.println("------------------------------------------------------");
    }
    //method to display books in library
    void displaybooks()
    {
        if(books.isEmpty())
            System.out.println("No book is in the library");
        else
        {
            for(Book b:books)
            {
                System.out.println(b);
            }
        }
        System.out.println("---------------------------------------------------------------------");
    }
}

class LibraryManagmentSystem
{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Library l1=new Library();
        l1.books.add(new Book(101,"1984 ","George Orwell"));
        l1.books.add(new Book(102,"The God of Small Things","Arundhati Roy"));
        l1.books.add(new Book(103,"War and Peace","Leo Tolstoy"));
        int choice;
        do
        {
            System.out.println("1.Add a book");
            System.out.println("2.Issue book");
            System.out.println("3.Return a book");
            System.out.println("4.Serach for a book by name");
            System.out.println("5.Check availability of a book");
            System.out.println("6.Check fine against a book");
            System.out.println("7.Report");
            System.out.println("8.Remainder");
            System.out.println("9.Check total fines collected");
            System.out.println("10.Display books present in the library");
            System.out.println("0.Exit");
            System.out.println("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice)
            {
                case 1:
                    l1.addbook();
                    break;
                case 2:
                    l1.issuebook();
                    break;
                case 3:
                    l1.returnbook();
                    break;
                case 4:
                    l1.searchbook();
                    break;
                case 5:
                    l1.checkavaliability();
                    break;
                case 6:
                    l1.collectedfine();
                    break;
                case 7:
                    l1.generatereport();
                    break;
                case 8:
                    l1.duereminder();
                    break;
                case 9:
                    l1.totalfine();
                    break;
                case 10:
                    l1.displaybooks();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Select valid option");
                    break;
            }
        }while(choice!=0);
        sc.close();
    }
}