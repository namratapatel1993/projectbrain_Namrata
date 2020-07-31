package com.lasalle.projectbrain.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostModel() {
    }

    /**
     *
     * @param data
     */
    public PostModel(List<Datum> data) {
        super();
        this.data = data;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Creator {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("followers")
        @Expose
        private List<Object> followers = null;
        @SerializedName("todos")
        @Expose
        private List<Todo> todos = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Creator() {
        }

        /**
         *
         * @param firstname
         * @param followers
         * @param city
         * @param todos
         * @param email
         * @param username
         * @param lastname
         */
        public Creator(String email, String username, String firstname, String lastname, String city, List<Object> followers, List<Todo> todos) {
            super();
            this.email = email;
            this.username = username;
            this.firstname = firstname;
            this.lastname = lastname;
            this.city = city;
            this.followers = followers;
            this.todos = todos;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Object> getFollowers() {
            return followers;
        }

        public void setFollowers(List<Object> followers) {
            this.followers = followers;
        }

        public List<Todo> getTodos() {
            return todos;
        }

        public void setTodos(List<Todo> todos) {
            this.todos = todos;
        }

    }
    public class Creator_ {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("followers")
        @Expose
        private List<Object> followers = null;
        @SerializedName("todos")
        @Expose
        private List<Object> todos = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Creator_() {
        }

        /**
         *
         * @param firstname
         * @param followers
         * @param city
         * @param todos
         * @param email
         * @param username
         * @param lastname
         */
        public Creator_(String email, String username, String firstname, String lastname, String city, List<Object> followers, List<Object> todos) {
            super();
            this.email = email;
            this.username = username;
            this.firstname = firstname;
            this.lastname = lastname;
            this.city = city;
            this.followers = followers;
            this.todos = todos;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Object> getFollowers() {
            return followers;
        }

        public void setFollowers(List<Object> followers) {
            this.followers = followers;
        }

        public List<Object> getTodos() {
            return todos;
        }

        public void setTodos(List<Object> todos) {
            this.todos = todos;
        }

    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("citeId")
        @Expose
        private String citeId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("context")
        @Expose
        private String context;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("creator")
        @Expose
        private Creator creator;

        /**
         * No args constructor for use in serialization
         *
         */
        public Datum() {
        }

        /**
         *
         * @param creator
         * @param context
         * @param id
         * @param title
         * @param content
         */
        public Datum(Integer id, String title, String context, String content, Creator creator) {
            super();
            this.id = id;
            this.title = title;
            this.context = context;
            this.content = content;
            this.creator = creator;
        }

        public Datum(Integer id, String citeId, String title, String context, String content, Creator creator) {
            super();
            this.id = id;
            this.citeId = citeId;
            this.title = title;
            this.context = context;
            this.content = content;
            this.creator = creator;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCiteId() {
            return citeId;
        }

        public void setCiteId(String citeId) {
            this.citeId = citeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Creator getCreator() {
            return creator;
        }

        public void setCreator(Creator creator) {
            this.creator = creator;
        }

    }
    public class Todo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("context")
        @Expose
        private String context;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("creator")
        @Expose
        private Creator_ creator;

        /**
         * No args constructor for use in serialization
         *
         */
        public Todo() {
        }

        /**
         *
         * @param creator
         * @param context
         * @param id
         * @param title
         * @param content
         */
        public Todo(Integer id, String title, String context, String content, Creator_ creator) {
            super();
            this.id = id;
            this.title = title;
            this.context = context;
            this.content = content;
            this.creator = creator;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Creator_ getCreator() {
            return creator;
        }

        public void setCreator(Creator_ creator) {
            this.creator = creator;
        }

    }

}

