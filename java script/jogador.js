class Jogador extends Objeto {
  constructor(x, y, jogo) {
    super(x, y, 150, 150);
    this.jogo = jogo;
    this.vel = 3;
    this.vidas = 3;
    this.cooldown = 0;
    this.nave = document.createElement("img");
    this.nave.classList.add("jogador");
    this.nave.src = "/ProjetoFinal/Imagens/gifNave.gif";
    this.nave.style.position = "absolute";
    this.nave.style.left = this.posX + "px";
    this.nave.style.top = this.posY + "px";
    this.nave.style.width = this.largura + "px";
    this.nave.style.height = this.altura + "px";
    document.body.appendChild(this.nave);
    
  }

  update(teclas) {
    

    if (teclas["KeyD"]) { 
        this.posX += this.vel;
        this.nave.style.left = this.posX+"px";
        
    }
    if (teclas["KeyA"]) { 
        this.posX -= this.vel;
        this.nave.style.left = this.posX+"px";
    }    
    if (teclas["Space"] && this.cooldown <= 0) {
      this.jogo.adicionarTiro(new Tiro(this.posX + this.largura / 2 - 45, this.posY - 10));
      this.cooldown = 70;
    }

    if (this.cooldown > 0) {
      this.cooldown--;
    }
    // Limites da tela
    if (this.posX < -40) {
      this.posX = -40;
    }
    if (this.posX + this.largura - 50 > this.jogo.largura) {
      this.posX = this.jogo.largura - this.largura + 50;
    }
  }

  remover() {
      if (this.nave) {
          this.nave.remove();
          this.nave = null;
      } 
  }

  getHitbox() {
      return {
          x: this.posX + 180,      // dá uma folga nas laterais
          y: this.posY - 160,      // sobe a hitbox, elimina espaço vazio em cima
          largura: this.largura - 70, // encurta largura
          altura: this.altura -100  // encurta altura
      };
  }

}
