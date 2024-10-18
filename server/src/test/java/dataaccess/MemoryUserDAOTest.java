package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class MemoryUserDAOTest {

    private MemoryUserDAO dao;
    private HashSet<UserData> expected;

    @BeforeEach
    public void setUp() {
        dao = new MemoryUserDAO();
        expected = new HashSet<>();
    }

    //test empty users
    @Test
    public void testUsersEmpty(){
        HashSet<UserData> actual = dao.getUsers();
        Assertions.assertEquals(expected, actual);
    }

    //test new user
    @Test
    public void testCreateUser(){
        UserData userData = new UserData("cs240student", "ilovecs", "student@byu.edu");

        dao.createUser(userData);

        expected.add(userData);

        HashSet<UserData> actual = dao.getUsers();
        Assertions.assertEquals(expected, actual);
    }

    //test new user but they already exist
    @Test
    public void testCreateUserDuplicate(){
        UserData userOne = new UserData("cs240student", "ilovecs", "student@byu.edu");
        UserData userTwo = new UserData("csanmstudent", "anim4life", "csanm@byu.edu");
        UserData userThree = new UserData("csanmstudent", "anim4life", "csanm@byu.edu");

        dao.createUser(userOne);
        dao.createUser(userTwo);
        dao.createUser(userThree);

        expected.add(userOne);
        expected.add(userTwo);
        expected.add(userThree);

        HashSet<UserData> actual = dao.getUsers();
        Assertions.assertEquals(expected, actual);
    }

    //test get user
    @Test
    public void testGetUser(){
        UserData userOne = new UserData("cs240student", "ilovecs", "student@byu.edu");
        UserData userTwo = new UserData("csanmstudent", "anim4life", "csanm@byu.edu");
        UserData userThree = new UserData("biostudent", "h20", "bio@byu.edu");

        dao.createUser(userOne);
        dao.createUser(userTwo);
        dao.createUser(userThree);

        expected.add(userOne);
        expected.add(userTwo);
        expected.add(userThree);

        UserData actual = dao.getUser("biostudent");
        Assertions.assertEquals(userThree, actual);
    }

    //test get user but dne
    @Test
    public void testGetUserDNE(){
        UserData userOne = new UserData("cs240student", "ilovecs", "student@byu.edu");
        UserData userTwo = new UserData("csanmstudent", "anim4life", "csanm@byu.edu");
        UserData userThree = new UserData("biostudent", "h20", "bio@byu.edu");

        dao.createUser(userOne);
        dao.createUser(userTwo);
        dao.createUser(userThree);

        expected.add(userOne);
        expected.add(userTwo);
        expected.add(userThree);

        UserData actual = dao.getUser("eledstudent");
        Assertions.assertNull(actual);
    }

    //test delete all users
    @Test
    public void testDeleteAllUsers() {
        UserData userOne = new UserData("cs240student", "ilovecs", "student@byu.edu");
        UserData userTwo = new UserData("csanmstudent", "anim4life", "csanm@byu.edu");
        UserData userThree = new UserData("biostudent", "h20", "bio@byu.edu");

        dao.createUser(userOne);
        dao.createUser(userTwo);
        dao.createUser(userThree);

        dao.deleteAllUsers();

        HashSet<UserData> actual = dao.getUsers();
        Assertions.assertEquals(expected, actual);
    }

    //test delete all users but empty
    @Test
    public void testDeleteAllUsersEmpty() {
        dao.deleteAllUsers();

        HashSet<UserData> actual = dao.getUsers();
        Assertions.assertEquals(expected, actual);
    }

}
