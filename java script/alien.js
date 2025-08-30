class Alien extends Objeto {
  constructor(x, y, largura, altura) {
    super(x, y, largura, altura);

    this.alien = document.createElement("img");
    this.alien.src = "/ProjetoFinal/Imagens/alien1.gif";
    this.alien.classList.add("alien");
    this.alien.style.position = "absolute";
    this.alien.style.left = this.posX + "px";
    this.alien.style.top = this.posY + "px";
    this.alien.style.width = this.largura + "px";
    this.alien.style.height = this.altura + "px";

    document.body.appendChild(this.alien);
  }

  update(teclas, jogo) {
    this.posX += jogo.velAliens * jogo.direcaoAliens;

    if (this.alien) {
      this.alien.style.left = this.posX + "px";
      this.alien.style.top = this.posY + "px";
    }
  }

  // pega a hitbox real do alien
  getHitbox() {
    return {
      x: this.posX + 180,      // dá uma folga nas laterais
      y: this.posY - 180,      // sobe a hitbox, elimina espaço vazio em cima
      largura: this.largura - 420, // encurta largura
      altura: this.altura - 100   // encurta altura
    };
  }
}