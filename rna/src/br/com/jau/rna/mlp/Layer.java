package br.com.jau.rna.mlp;

class Layer {

    private Neuron[] neurons;
    private double[] output;
    private ActivationFunction f;

    /**
     * Construtor da camada.
     *
     * @param n n�mero de neur�nios na camada
     * @param connections n�mero de conex�es por neur�nio
     */
    Layer(int n, int connections) {

        f = new ActivationFunction();

        neurons = new Neuron[n];
        output = new double[n];

        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(connections);

            // Iniciando cada neur�nio com valores aleat�rios
            neurons[i].randomize();
        }
    }

    void compute(double[] input) {

        for (int i = 0; i < neurons.length; i++) {
            output[i] = f.function(net(i, input));
        }
    }

    int getSize() {
        return neurons.length;
    }

    public String toString() {
        return getClass().getName() + "[" + neurons.length + "]";
    }

    final double getOutput(int i) {
        return output[i];
    }

    /*
     * Quebra o encapsulamento, mas clonar o objeto v�rias vezes tamb�m
     * n�o � uma boa id�ia neste caso.
     */
    final double[] getOutput() {
        return output;
    }

    final Neuron getNeuron(int i) {
        return neurons[i];
    }

    final int getNeuronSize() {
        return neurons[0].getSize();
    }

    private double net(int neuron, double[] input) {
        double s = 0;

        for (int i = 0; i < input.length; i++) {
            s += input[i] * neurons[neuron].getWeight(i);
        }

        s -= neurons[neuron].getBias();

        return s;
    }
}
