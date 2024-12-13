# Classe Router

A classe `Router` representa um roteador em uma rede que utiliza algoritmos de roteamento para determinar o melhor caminho para enviar mensagens.

## Atributos

- `id: String`: Identificador único do roteador.
- `graph: Graph<String, DefaultEdge>`: Grafo que representa a topologia da rede.
- `neighbors: Map<String, Router>`: Mapa dos roteadores vizinhos e seus pesos.
- `algorithm: Algorithm<String>`: Algoritmo de roteamento utilizado pelo roteador.
- `visualizer: GraphVisualizer<String>`: Visualizador do grafo para exibição gráfica.

## Construtor

- `Router(id: String, algorithm: Algorithm<String>)`: Inicializa um roteador com um identificador e um algoritmo de roteamento.

## Métodos

- `addNeighbor(neighbor: Router, weight: int): void`: Adiciona um roteador vizinho com um peso especificado.
- `sendMessage(message: String, destinationId: String): void`: Envia uma mensagem para um roteador de destino.
- `receiveMessage(message: String, receivedGraph: Graph<String, DefaultEdge>): void`: Recebe uma mensagem e atualiza o grafo com base no grafo recebido.
- `printGraph(): void`: Imprime a representação do grafo.
- `notifyNeighbors(newNeighbor: Router): void`: Notifica os vizinhos sobre um novo vizinho.
- `notifyNeighbors(): void`: Notifica os vizinhos sobre mudanças na topologia.
- `mergeGraphs(receivedGraph: Graph<String, DefaultEdge>): void`: Mescla o grafo recebido com o grafo atual.
- `static setVisualizer(visualizer: GraphVisualizer<String>): void`: Define o visualizador do grafo.

### Resumo

1. **Inicialização**: Cria um roteador com um identificador e um algoritmo de roteamento.
2. **Adicionar Vizinho**: Adiciona um roteador vizinho com um peso especificado.
3. **Enviar Mensagem**: Envia uma mensagem para um roteador de destino.
4. **Receber Mensagem**: Recebe uma mensagem e atualiza o grafo com base no grafo recebido.
5. **Imprimir Grafo**: Imprime a representação do grafo.
6. **Notificar Vizinhos**: Notifica os vizinhos sobre mudanças na topologia.
7. **Mesclar Grafos**: Mescla o grafo recebido com o grafo atual.
8. **Definir Visualizador**: Define o visualizador do grafo.