class Objeto {
  constructor(x, y, largura, altura) {
    this.posX = x;
    this.posY = y;
    this.largura = largura;
    this.altura = altura;
  }

  update() {
    // cada subclasse implementa sua própria lógica
  }

  /*draw(ctx) {
    // cada subclasse implementa sua própria renderização
  }*/

  colideCom(outro) {
    return (
      this.posX < outro.posX + outro.largura &&
      this.posX + this.largura > outro.posX &&
      this.posY < outro.posY + outro.altura &&
      this.posY + this.altura > outro.posY
    );
  }
}
