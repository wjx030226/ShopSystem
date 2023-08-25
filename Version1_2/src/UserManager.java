import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    Scanner reader;
    int id;
    String password;
    //用户注册
    public void register(ArrayList<Users> users, boolean isUser)
    {
        System.out.print("请输入用户身份码：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        System.out.print("请输入账号名称：");
        reader = new Scanner(System.in);
        String name = reader.nextLine();
        System.out.print("请输入邮箱：");
        reader = new Scanner(System.in);
        String email = reader.nextLine();
        System.out.print("请输入账号密码：");
        reader = new Scanner(System.in);
        password = reader.nextLine();
        while(true){
            System.out.print("请再次输入账号密码：");
            reader = new Scanner(System.in);
            if(password.equals(reader.nextLine())){
                for (Users user : users) {
                    if (user.userID == id) {
                        System.out.println("身份码已被注册，注册失败！");
                        return;
                    }
                }
                if (name.length() <= 5)
                    System.out.println("非法用户名输入，注册失败！");
                else if(JudgePassword(password))
                    System.out.println("非法密码输入，注册失败！");
                else{
                    Users user = new Users(id, name, getMD5(password), email, isUser);
                    users.add(user);
                    System.out.println("注册成功！");
                }
                return;
            }
            else System.out.println("两次输入密码不匹配，请重新输入！");
        }
    }
    //用户登录
    public int login(ArrayList<Users> users, boolean isUser)
    {
        System.out.print("请输入用户身份码：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        System.out.print("请输入账号密码：");
        reader = new Scanner(System.in);
        password = getMD5(reader.nextLine());
        for(Users user:users) {
            if (user.userID == id){
                if (isUser == user.isUser){
                    while(!user.password.equals(password)){
                        System.out.println("账号名或密码错误，请重新输入！");
                        System.out.print("请输入账号密码：");
                        reader = new Scanner(System.in);
                        password = getMD5(reader.nextLine());
                    }
                    System.out.println("登录成功！");
                    return id;
                }
                else{
                    System.out.println("用户与管理员身份混淆，登录失败！");
                    return 0;
                }
            }
        }
        System.out.println("身份码验证失败！");
        return 0;
    }
    //修改密码
    public void change(ArrayList<Users> users, int id)
    {
        System.out.print("请输入原来的账号密码：");
        reader = new Scanner(System.in);
        password = getMD5(reader.nextLine());
        for(Users user:users) {
            if(user.userID == id){
                while(!user.password.equals(password)){
                    System.out.println("密码错误，请重新输入！");
                    System.out.print("请输入原来的账号密码：");
                    reader = new Scanner(System.in);
                    password = getMD5(reader.nextLine());
                }
                System.out.print("请输入新的账号密码：");
                reader = new Scanner(System.in);
                password = reader.nextLine();
                while(JudgePassword(password)){
                    System.out.println("非法密码输入，请重新输入！");
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    password = reader.nextLine();
                }
                user.password = getMD5(password);
                System.out.println("密码修改成功！");
                return;
            }
        }
    }
    //重置密码
    public void resent(ArrayList<Users> users)
    {
        System.out.print("请输入您的身份码：");
        reader = new Scanner(System.in);
        id= reader.nextInt();
        for(Users user:users) {
            if(user.userID == id){
                System.out.print("请输入验证的邮箱：");
                reader = new Scanner(System.in);
                String email= reader.nextLine();
                while(!user.email.equals(email)){
                    System.out.println("邮箱验证失败！");
                    System.out.print("请重新输入验证的邮箱：");
                    reader = new Scanner(System.in);
                    email = reader.nextLine();
                }
                System.out.println("邮箱验证成功！");
                System.out.print("请输入新的账号密码：");
                reader = new Scanner(System.in);
                password = reader.nextLine();
                while(JudgePassword(password)){
                    System.out.println("非法密码输入，请重新输入！");
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    password = reader.nextLine();
                }
                user.password = getMD5(password);
                System.out.println("密码重置成功！");
                return;
            }
        }
        System.out.println("用户不存在，请先注册！");
    }
    //重置用户密码
    public void modify(ArrayList<Users> users){
        System.out.print("请输入想要的修改的用户身份码：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Users user:users) {
            if(user.userID == id){
                System.out.print("请输入用户的验证邮箱：");
                reader = new Scanner(System.in);
                String email = reader.nextLine();
                while(!email.equals(user.email)){
                    System.out.println("验证邮箱输入有误，请重新输入！");
                    System.out.print("请输入用户的验证邮箱：");
                    reader = new Scanner(System.in);
                    email = reader.nextLine();
                }
                System.out.print("请输入想要的修改的账号密码：");
                reader = new Scanner(System.in);
                password = reader.nextLine();
                while(JudgePassword(password)){
                    System.out.println("非法密码输入，请重新输入！");
                    System.out.print("请输入新的账号密码：");
                    reader = new Scanner(System.in);
                    password = reader.nextLine();
                }
                user.password = getMD5(password);
                System.out.println("密码修改成功！");
                return;
            }
        }
        System.out.println("想要修改的用户不存在！");
    }
    //判断密码是否满足要求，即包含大小写字母、数字以及特殊字符
    public boolean JudgePassword(String password)
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return !matcher.matches();
    }
    //删除用户
    public void delete(ArrayList<Users> users)
    {
        System.out.print("请输入想要删除用户身份码：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        System.out.print("确定要删除该用户信息(Y/N)？");
        reader = new Scanner(System.in);
        String judge= reader.nextLine();
        if(judge.equals("Y")){
            for(Users user:users) {
                if(user.userID == id && user.isUser){
                    users.remove(user);
                    System.out.println("用户已删除！");
                    return;
                }
            }
            System.out.println("未找到指定用户！");
        }
        else
            System.out.println("用户删除失败！");
    }
    //查询用户
    public void find(ArrayList<Users> users)
    {
        System.out.print("请输入想要查找的用户身份码：");
        reader = new Scanner(System.in);
        id = reader.nextInt();
        for(Users user:users) {
            if(user.userID == id && user.isUser) {
                System.out.println("要查找的用户的信息为：");
                System.out.println("用户身份码:"+user.userID+"  用户名称:"+user.username+"  加密密码:"+user.password+"  邮箱:"+user.email);
                return;
            }
        }
        System.out.println("想要查找的用户不存在!");
    }
    //显示所有用户信息
    public void print(ArrayList<Users> users)
    {
        System.out.println("用户身份码      用户名称                      加密密码                              邮箱");
        System.out.println("------------------------------------------------------------------------------------");
        for(Users user:users){
            if(user.isUser)
                System.out.println(user.userID+"       "+user.username+"       "+user.password+"       "+user.email);
        }
    }
    //利用MD5技术对密码进行加密
    public String getMD5(String str)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            byte[] mdBytes = md.digest();
            StringBuilder passwordMD = new StringBuilder();
            for (byte mdByte : mdBytes) {
                int tmp;
                if (mdByte < 0) tmp = 256 + mdByte;
                else tmp = mdByte;
                if (tmp < 16) passwordMD.append("0");
                passwordMD.append(Integer.toString(tmp, 16));
            }
            return passwordMD.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    //读取文件
    public void readFile(String path, ArrayList<Users> users)
    {
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path), "GBK"));
            String line;
            String[] row;
            input.readLine();
            while((line = input.readLine()) != null){
                row = line.split(",");
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String password = row[2];
                String email = row[3];
                String isUser = row[4];
                Users tmp;
                if(isUser.equals("用户")) tmp = new Users(id, name, password, email, true);
                else tmp = new Users(id, name, password, email, false);
                users.add(tmp);
            }
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //将用户信息写入文件
    public void writeFile(String path, ArrayList<Users> users)
    {
        try{
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "GBK"));
            output.write("用户身份码,用户名称,加密密码,邮箱,身份");
            output.newLine();
            for(Users user : users){
                output.write(user.userID+",");
                output.write(user.username+",");
                output.write(user.password+",");
                output.write(user.email+",");
                if(user.isUser) output.write("用户");
                else output.write("管理员");
                output.newLine();
            }
            output.flush();
            output.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}