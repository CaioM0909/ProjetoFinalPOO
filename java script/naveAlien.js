class NaveAlien extends Objeto {
  constructor(xMax) {
    super(-400, -270, 700, 700); 
    this.xAtual = -400;
    this.xAdicional = 1; // velocidade
    this.xMax = xMax;    // limite da tela
    this.vidas = 3;

    // cria a nave no DOM
    this.element = document.createElement("img");
    this.element.src = "/ProjetoFinal/Imagens/NaveAlien.gif"; 
    this.element.style.position = "absolute";
    this.element.style.left = this.posX + "px";
    this.element.style.top = this.posY + "px";
    this.element.style.width = this.largura + "px";
    this.element.style.height = this.altura + "px";
    
    document.body.appendChild(this.element);

    //define se a nave morreu
    this.morto = false;
  }

  update() {
    // Movimento horizontal
    if (this.xAtual > this.xMax - 400) {
      this.xAdicional *= -1;
    }
    this.xAtual += this.xAdicional;

    // Atualiza posição
    this.posX = this.xAtual;
    this.element.style.left = this.posX + "px";
    this.element.style.top = this.posY + "px";

    // Se saiu totalmente pela esquerda → remove
    if (this.xAtual < -400) {
      this.remover();
      this.morto = true;
    }

    
  }

  remover() {
    this.element.remove();
  }

  setVidas(num) {
    this.vidas = num;
  }

  getVidas() {
    return this.vidas;
  }

  getHitbox() {
    return {
      x: this.posX + 270,
      y: this.posY - 260,
      largura: this.largura - 600,
      altura: this.altura - 140
    };
    
  }

  PerdeVida(jogo) {
    this.vidas--;
    console.log("tomanocu");
    if (this.vidas <= 0) {
      this.morto = true;
      this.element.remove();
      // pontuação ao destruir a nave alien
      jogo.score(1000);
    }
  }
}