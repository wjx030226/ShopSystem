public class Users {
    int userID;  //用户身份码
    String username;  //用户姓名
    String password;  //用户密码
    String email;  //用户年龄
    boolean isUser;  //是否是管理员
    public Users(int id, String username, String password,String email, boolean isUser) {
        this.userID = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isUser = isUser;
    }
}
