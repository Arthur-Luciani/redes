# Algoritmos de Roteamento

## Interface Algorithm

A interface `Algorithm` define dois métodos que devem ser implementados por qualquer algoritmo de roteamento:

1. `recalculate(graph: Graph<V, DefaultEdge>, startVertex: V): void`: Este método recalcula os caminhos mais curtos do `startVertex` para todos os outros vértices no `graph` fornecido.
2. `getNextHop(destination: V): V`: Este método retorna o próximo vértice no caminho para o `destination` especificado.

## Algoritmo BellmanFord

A classe `BellmanFord` implementa a interface `Algorithm` usando o algoritmo de Bellman-Ford. Ele recalcula os caminhos mais curtos iterando sobre todas as arestas e atualizando as distâncias. Ele lida com pesos negativos e pode detectar ciclos negativos.

### Pseudocódigo

1. Inicializar distâncias de todos os vértices para infinito, exceto a origem (0).
2. Para cada aresta, atualizar a distância se um caminho mais curto for encontrado.
3. Verificar a existência de ciclos negativos.

## Algoritmo PathVector

A classe `PathVector` implementa a interface `Algorithm` usando um algoritmo de vetor de caminhos. Ele recalcula os caminhos mais curtos trocando vetores de distância entre os nós, semelhante ao Protocolo de Roteamento por Vetor de Distância.

### Pseudocódigo

1. Inicializar distâncias de todos os vértices para infinito, exceto a origem (0).
2. Cada nó troca vetores de distância com seus vizinhos.
3. Atualizar distâncias com base nas informações recebidas dos vizinhos.

## Algoritmo Dijkstra

A classe `Dijkstra` implementa a interface `Algorithm` usando o algoritmo de Dijkstra. Ele recalcula os caminhos mais curtos usando uma fila de prioridade para explorar o vértice não visitado mais próximo e atualizar as distâncias. Ele assume que todos os pesos das arestas são não-negativos.

### Pseudocódigo

1. Inicializar distâncias de todos os vértices para infinito, exceto a origem (0).
2. Usar uma fila de prioridade para explorar o vértice não visitado mais próximo.
3. Atualizar distâncias dos vizinhos se um caminho mais curto for encontrado.
4. Repetir até que todos os vértices tenham sido visitados.