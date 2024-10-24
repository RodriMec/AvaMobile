import { Button, Text, View } from "react-native";

export function HomeScreen({ navigation }) {
    return (
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
            <Text>Home Screen</Text>
            <Button
                title="Ir para detalhes"
                onPress={() => navigation.navigate("Details")}
            ></Button>
            <Button
                title="Lista de Filmes"
                onPress={() => navigation.navigate("Movies")}
            ></Button>
        </View>
    );
}

