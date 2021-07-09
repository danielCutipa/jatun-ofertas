package com.jatun.ofertas;

import com.jatun.ofertas.bean.MyBean;
import com.jatun.ofertas.bean.MyBeanWithDependency;
import com.jatun.ofertas.bean.MyBeanWithProperties;
import com.jatun.ofertas.component.ComponentDependency;
import com.jatun.ofertas.entity.User;
import com.jatun.ofertas.pojo.UserPojo;
import com.jatun.ofertas.repository.UserRepository;
import com.jatun.ofertas.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class OfertasApplication implements CommandLineRunner {

    Log LOGGER = LogFactory.getLog(OfertasApplication.class);

    private ComponentDependency componentDependency;
    private MyBean myBean;
    private MyBeanWithDependency myBeanWithDependency;
    private MyBeanWithProperties myBeanWithProperties;
    private UserPojo userPojo;
    private UserRepository userRepository;
    private UserService userService;

    public OfertasApplication(
            @Qualifier("componentTwoImplement") ComponentDependency componentDependency,
            MyBean myBean,
            MyBeanWithDependency myBeanWithDependency,
            MyBeanWithProperties myBeanWithProperties,
            UserPojo userPojo,
            UserRepository userRepository,
            UserService userService
    ) {
        this.componentDependency = componentDependency;
        this.myBean = myBean;
        this.myBeanWithDependency = myBeanWithDependency;
        this.myBeanWithProperties = myBeanWithProperties;
        this.userPojo = userPojo;
        this.userRepository = userRepository;
        this.userService = userService;

    }

    public static void main(String[] args) {
        SpringApplication.run(OfertasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // ejemplosAnteriores();
        saveUserInDataBase();
        getInformationJpqlFromUser();
        saveWithErrorTransactional();
    }

    private void saveWithErrorTransactional() {
        User test1 = new User("TestTransactional1", "test1@mail.com", LocalDate.of(2021, 3, 20));
        User test2 = new User("TestTransactional2", "test2@mail.com", LocalDate.of(2021, 3, 20));
        User test3 = new User("TestTransactional3", "test1@mail.com", LocalDate.of(2021, 3, 20));
        User test4 = new User("TestTransactional4", "test4@mail.com", LocalDate.of(2021, 3, 20));

        List<User> users = Arrays.asList(test1, test2, test3, test4);
        try {
            userService.saveTransactional(users);

        } catch (Exception e) {
            LOGGER.error("Esto es una exception dentro del metodo transaccional " + e);
        }

        userService.getAllUsers()
                .stream()
                .forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional " + user));

    }

    private void getInformationJpqlFromUser() {
        /*
        LOGGER.info("Usuario con el metodo findByUserEmail" +
                userRepository
                        .findByUserEmail("julie@mail.com")
                        .orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

        userRepository.findAndSort("user", Sort.by("id").descending())
                .stream()
                .forEach(user -> LOGGER.info("Usuario con metodo sort " + user));

        userRepository.findByName("John")
                .stream()
                .forEach(user -> LOGGER.info("Usuario con query method " + user.toString()));

        LOGGER.info("Usuario con query method findByEmailAndName" + userRepository.findByEmailAndName("pedro@mail.com", "Pedro")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        userRepository.findByNameLike("%u%")
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameLike" + user));

        userRepository.findByNameOrEmail(null, "julie@mail.com")
                .stream()
                .forEach(user -> LOGGER.info("Usuario findByNameOrEmail" + user));
         */

        userRepository.findByBirthDateBetween(
                LocalDate.of(2021, 4, 2),
                LocalDate.of(2021, 8, 2))
                .stream()
                .forEach(user -> LOGGER.info("Usuario con intervalo de fechas findByBirthDateBetween " + user));

        userRepository.findByNameContainingOrderByIdDesc("user")
                .stream()
                .forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado " + user));

        LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(
                LocalDate.of(2021, 6, 20),
                "pedro@mail.com")
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));


    }

    private void saveUserInDataBase() {
        User user1 = new User("John", "john@mail.com", LocalDate.of(2021, 3, 20));
        User user2 = new User("Julie", "julie@mail.com", LocalDate.of(2021, 5, 20));
        User user3 = new User("Pedro", "pedro@mail.com", LocalDate.of(2021, 6, 20));
        User user4 = new User("user4", "user4@mail.com", LocalDate.of(2021, 7, 20));
        User user5 = new User("user5", "user5@mail.com", LocalDate.of(2021, 8, 20));
        User user6 = new User("user6", "user6@mail.com", LocalDate.of(2021, 9, 20));
        User user7 = new User("user7", "user7@mail.com", LocalDate.of(2021, 1, 20));
        User user8 = new User("user8", "user8@mail.com", LocalDate.of(2021, 2, 20));
        User user9 = new User("user9", "user9@mail.com", LocalDate.of(2021, 3, 20));
        User user10 = new User("user10", "user10@mail.com", LocalDate.of(2021, 4, 20));

        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
        list.stream().forEach(userRepository::save);
    }

    private void ejemplosAnteriores() {
        componentDependency.saludar();
        myBean.print();
        myBeanWithDependency.printWithDependency();
        System.out.println(myBeanWithProperties.function());
        System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());

        try {
            // error
            int value = 10 / 0;
            LOGGER.debug("Mi valor :" + value);
        } catch (Exception e) {

            LOGGER.error("Esto es un error al dividir por cero" + e.getMessage());
        }
    }
}
