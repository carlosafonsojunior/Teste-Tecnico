public class Main {
    public static void main(String[] args) {
        // Exemplo de uso
        PersonController personController = new PersonController();
        Person person = personController.createPerson("John Doe", "1990-01-01");

        System.out.println("Person ID: " + person.getId());
        System.out.println("Person Name: " + person.getFullName());
        System.out.println("Person Birth Date: " + person.getBirthDate());
    }
}
