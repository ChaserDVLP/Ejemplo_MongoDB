package MongoDB.EjemploMongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class App {
	public static void main(String[] args) {
		
		//URI de conexión a tu cluster remoto MongoDB
        String connectionString = "mongodb+srv://xChaser:1234@cluster2.acmzvmr.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
            	
            	//Como establecer conexión con el servidor mongoDB LOCAL
            	//try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"))
                
            	//Obtener la base de datos
                MongoDatabase database = mongoClient.getDatabase("xChaser");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                
                //Obtener la colección
                MongoCollection<Document> collection = database.getCollection("mi_coleccion");
                
                //Insertar documento de ejemplo
                Document document = new Document("nombre", "Ejemplo")
                		.append("edad", 30)
                		.append("ciudad", "EjemploCity");
                collection.insertOne(document); //inseramos en el documento
                
                //Consultar e imprimir todos los documentos de la colección
                MongoCursor<Document> cursor = collection.find().iterator();
                try {
                	while (cursor.hasNext()) {
                		System.out.println(cursor.next().toJson());
                	}
                } finally {
                	cursor.close();
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
