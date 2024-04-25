import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressController {
    private Connection connection;

    // Método para estabelecer conexão com o banco de dados
    public AddressController() {
        try {
            // Conecte-se ao seu banco de dados
            connection = DriverManager.getConnection("jdbc:mysql://localhost:????/banco_de_dados", "usuario", "senha");
            // mudar para conectar ao banco a ser utilizado
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Address createAddress(int personId, String street, String cep, String number, String city, String state) {
        try {
            // Verifique se a pessoa existe antes de criar o endereço
            PreparedStatement checkPerson = connection.prepareStatement("SELECT id FROM Persons WHERE id = ?");
            checkPerson.setInt(1, personId);
            ResultSet personResult = checkPerson.executeQuery();
            if (!personResult.next()) {
                System.out.println("Pessoa com ID " + personId + " não encontrada.");
                return null;
            }

            // Insira o endereço na tabela Addresses
            PreparedStatement insertAddress = connection.prepareStatement(
                "INSERT INTO Addresses (person_id, street, cep, number, city, state) VALUES (?, ?, ?, ?, ?, ?)"
            );
            insertAddress.setInt(1, personId);
            insertAddress.setString(2, street);
            insertAddress.setString(3, cep);
            insertAddress.setString(4, number);
            insertAddress.setString(5, city);
            insertAddress.setString(6, state);
            insertAddress.executeUpdate();

            // Recupere o ID do endereço recém-criado
            ResultSet generatedKeys = insertAddress.getGeneratedKeys();
            if (generatedKeys.next()) {
                int addressId = generatedKeys.getInt(1);
                // Consulte o endereço criado e retorne-o
                return getAddressById(addressId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Address updateAddress(int personId, int addressId, String street, String cep, String number, String city, String state) {
        try {
            // Verifique se a pessoa existe antes de atualizar o endereço
            PreparedStatement checkPerson = connection.prepareStatement("SELECT id FROM Persons WHERE id = ?");
            checkPerson.setInt(1, personId);
            ResultSet personResult = checkPerson.executeQuery();
            if (!personResult.next()) {
                System.out.println("Pessoa com ID " + personId + " não encontrada.");
                return null;
            }

            // Verifique se o endereço existe antes de atualizá-lo
            PreparedStatement checkAddress = connection.prepareStatement("SELECT id FROM Addresses WHERE id = ?");
            checkAddress.setInt(1, addressId);
            ResultSet addressResult = checkAddress.executeQuery();
            if (!addressResult.next()) {
                System.out.println("Endereço com ID " + addressId + " não encontrado.");
                return null;
            }

            // Atualize o endereço na tabela Addresses
            PreparedStatement updateAddress = connection.prepareStatement(
                "UPDATE Addresses SET street = ?, cep = ?, number = ?, city = ?, state = ? WHERE id = ?"
            );
            updateAddress.setString(1, street);
            updateAddress.setString(2, cep);
            updateAddress.setString(3, number);
            updateAddress.setString(4, city);
            updateAddress.setString(5, state);
            updateAddress.setInt(6, addressId);
            updateAddress.executeUpdate();

            // Consulte o endereço atualizado e retorne-o
            return getAddressById(addressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMainAddress(int personId, int addressId) {
        try {
            // Verifique se a pessoa existe antes de definir o endereço principal
            PreparedStatement checkPerson = connection.prepareStatement("SELECT id FROM Persons WHERE id = ?");
            checkPerson.setInt(1, personId);
            ResultSet personResult = checkPerson.executeQuery();
            if (!personResult.next()) {
                System.out.println("Pessoa com ID " + personId + " não encontrada.");
                return;
            }

            // Verifique se o endereço existe antes de definir como principal
            PreparedStatement checkAddress = connection.prepareStatement("SELECT id FROM Addresses WHERE id = ?");
            checkAddress.setInt(1, addressId);
            ResultSet addressResult = checkAddress.executeQuery();
            if (!addressResult.next()) {
                System.out.println("Endereço com ID " + addressId + " não encontrado.");
                return;
            }

            // Defina o endereço como principal (definindo todos os outros como não principais)
            PreparedStatement setMainAddress = connection.prepareStatement(
                "UPDATE Addresses SET is_main_address = CASE WHEN id = ? THEN true ELSE false END WHERE person_id = ?"
            );
            setMainAddress.setInt(1, addressId);
            setMainAddress.setInt(2, personId);
            setMainAddress.executeUpdate();

            System.out.println("Endereço principal definido com sucesso para a pessoa com ID " + personId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para consultar um endereço pelo ID
    private Address getAddressById(int addressId) throws SQLException {
        PreparedStatement getAddress = connection.prepareStatement("SELECT * FROM Addresses WHERE id = ?");
        getAddress.setInt(1, addressId);
        ResultSet addressResult = getAddress.executeQuery();
        if (addressResult.next()) {
            return new Address(
                addressResult.getString("street"),
                addressResult.getString("cep"),
                addressResult.getString("number"),
                addressResult.getString("city"),
                addressResult.getString("state")
            );
        } else {
            return null;
        }
    }
}
