//Accepting operation, mob no, name, course, fees
import java.io.*;
class MemberManager
{
private static final String DATA_FILE= "members.data";
public static void main(String []gg)
{
if(gg.length==0)
{
System.out.println("Please Specity some operation");
return;
}
if(!isOperationValid((gg[0].trim())))
{
System.out.println("Invalid Operation: "+gg[0]);
System.out.println("Operations are: [Add, Update, remove, getAll, getByContactNumber, getByCourse]");
return;
}
String operation= gg[0].trim();
if((operation.equalsIgnoreCase("add")))
{
add(gg);
} else
if((operation.equalsIgnoreCase("update")))
{
update(gg);
} else
if((operation.equalsIgnoreCase("remove")))
{
remove(gg);
} else

if((operation.equalsIgnoreCase("getAll")))
{
getAll(gg);
} else

if((operation.equalsIgnoreCase("getByContactNumber")))
{
getByContactNumber(gg);
} else

if((operation.equalsIgnoreCase("getByCourse")))
{
getByCourse(gg);
}
}

//operations
private static void add(String []data)
{
if(data.length!=5) 
{
System.out.println("Not enough data given!");
return;
}
String mobileNumber= data[1];
String name= data[2];
if(!isCourseValid(data[3])) 
{
System.out.println("Invalid Course given: "+data[3]);
return;
}
String course= data[3];
int fees=0;
try
{
fees= Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee should be an int type value");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file, "rw");
String fMobileNumber=data[1];
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
randomAccessFile.close(); 
System.out.println(mobileNumber+ "exists!");
return;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.writeBytes(mobileNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(name);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(course);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(fees));
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
System.out.println("Data Added");

}catch(IOException ioexception)
{
System.out.println(ioexception.getMessage());
return;
}
} 

private static void update(String []data)
{
if(data.length!=5)
{
System.out.println("Wrong no of arguments passed!");
return;
}
if(!isCourseValid(data[3]))
{
System.out.println("Course not found!");
return;
}
String mobileNumber=data[1];
String name=data[2];
String course=data[3];
int fee=0;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Invalid Fee!");
return;
}
try
{
File file=new File(DATA_FILE);
if(!file.exists())
{
System.out.println("Contact not found!");
return;
}
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Contact not found!");
return;
}
String fMobileNumber="";
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
if(fMobileNumber.equalsIgnoreCase(mobileNumber))
{
found=true;
break;
}
}
if(!found)
{
raf.close();
System.out.println("Contact not found!");
return;
}
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpraf= new RandomAccessFile(tmpFile, "rw");
tmpraf.seek(0);
tmpraf.setLength(0);
raf.seek(0);
String fName="";
String fCourse="";
int fFee=0;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFee=Integer.parseInt(raf.readLine());
if(!fMobileNumber.equalsIgnoreCase(mobileNumber))
{
tmpraf.writeBytes(fMobileNumber+"\n");
tmpraf.writeBytes(fName+"\n");
tmpraf.writeBytes(fCourse+"\n");
tmpraf.writeBytes(fFee+"\n");
}
else
{
tmpraf.writeBytes(mobileNumber+"\n");
tmpraf.writeBytes(name+"\n");
tmpraf.writeBytes(course+"\n");
tmpraf.writeBytes(fee+"\n");
}
}
raf.seek(0);
tmpraf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length())
{
raf.writeBytes(tmpraf.readLine()+"\n");
}
raf.setLength(tmpraf.length());
tmpraf.setLength(0);
System.out.println("Data Updated");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}

private static void remove(String []data)
{
if(data.length!=2)
{
System.out.println("Wrong no of arguments passed!");
return;
}
String mobileNumber=data[1];
try
{
File file=new File(DATA_FILE);
if(!file.exists())
{
System.out.println("Contact not found!");
return;
}
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Contact not found!");
return;
}
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpraf= new RandomAccessFile(tmpFile, "rw");
tmpraf.seek(0);
tmpraf.setLength(0);
raf.seek(0);

String fMobileNumber="";
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fMobileNumber=raf.readLine();
if(fMobileNumber.equalsIgnoreCase(data[1]))
{
found=true;
raf.readLine();
raf.readLine();
raf.readLine();
continue;
}
else
{
tmpraf.writeBytes(fMobileNumber+"\n");
tmpraf.writeBytes(raf.readLine()+"\n");
tmpraf.writeBytes(raf.readLine()+"\n");
tmpraf.writeBytes(raf.readLine()+"\n");
}
}
if(!found)
{
raf.close();
tmpraf.setLength(0);
System.out.println("Contact not found!");
return;
}
raf.seek(0);
tmpraf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length())
{
raf.writeBytes(tmpraf.readLine()+"\n");
}
raf.setLength(tmpraf.length());
tmpraf.setLength(0);
System.out.println("Data Removed");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}

private static void getAll(String []data)
{
try
{
int total=0, count=0, fees=0;
File file= new File(DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file, "rw");
if(!file.exists())
{
randomAccessFile.close();
System.out.println("No Members");
return;
}
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No Members");
return;
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
count++;
System.out.println(("----------Student: "+count)+"----------------------");
System.out.println("Mobile Number: "+randomAccessFile.readLine());
System.out.println("Name: "+randomAccessFile.readLine());
System.out.println("Course: "+randomAccessFile.readLine());
fees=Integer.parseInt(randomAccessFile.readLine());
System.out.println("Fees: "+fees);
total+=fees;
}
System.out.println("\nTotal Amount: "+total);
randomAccessFile.close();
}catch(IOException ioexception)
{
System.out.println(ioexception.getMessage());
return;
}
}
private static void getByContactNumber(String []data)
{
if(data.length!=2)
{
System.out.println("Not enough arguments specified");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(!file.exists())
{
raf.close();
System.out.println("No Contact Found");
return;
}
if(raf.length()==0)
{
raf.close();
System.out.println("No Contact Found");
return;
}
while(raf.getFilePointer()<raf.length())
{
if(data[1].equalsIgnoreCase(raf.readLine()))
{
System.out.println("Contact Found. Here are its credentials: ");
System.out.println("Name: "+raf.readLine());
System.out.println("Course: "+raf.readLine());
System.out.println("Fee: "+Integer.parseInt(raf.readLine()));
raf.close();
return;
}
raf.readLine();
raf.readLine();
raf.readLine();
}
System.out.println("No records found associated to: "+data[1]);
raf.close();
}catch(IOException ioe)
{
System.out.println("No records found associated to: "+data[1]);
return;
}
}
private static void getByCourse(String []data)
{
int count=0;
int totalFee=0;
boolean found=false;
String course=data[1];
if(!isCourseValid(course))
{
System.out.println("Invalid Course");
return;
}
if(data.length==0)
{
System.out.println("No registrations done");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(!file.exists())
{
raf.close();
System.out.println("No registrations done");
return;
}
if(raf.length()==0)
{
raf.close();
System.out.println("No registrations done");
return;
}
String mobileNumber="";
String name="";
String fCourse="";
int fee=0;
while(raf.getFilePointer()<raf.length())
{
mobileNumber=raf.readLine();
name=raf.readLine();
fCourse=raf.readLine();
fee=Integer.parseInt(raf.readLine());
if(fCourse.equalsIgnoreCase(course))
{
System.out.println("Student: "+(++count));
System.out.println("Mobile Number: "+mobileNumber);
System.out.println("Name: "+name);
System.out.println("Course: "+course);
System.out.println("Fee: "+fee);
totalFee+=fee;
System.out.println("----------------------------------------------------");
found=true;
}
}
if(!found) System.out.println("No registration againt: "+course);
System.out.println("Total Fee: "+totalFee);
raf.close();
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}

//Helper Functions
private static boolean isOperationValid(String operation)
{
String operations[] ={"add", "update", "remove", "getAll", "getByContactNumber", "getByCourse"};
for(int i=0; i<operations.length; i++)
{
if(operations[i].equalsIgnoreCase(operation)) return true;
}
return false;
}
private static boolean isCourseValid(String course)
{
String courseList[]= {"C","C++","Java","Python","J2EE"};
for(int i=0; i<courseList.length; i++)
{
if(courseList[i].equalsIgnoreCase(course)) return true;
}
return false;
}

}