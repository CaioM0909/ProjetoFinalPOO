class NaveAlien extends Objeto {
  constructor(xMax) {
    super(-400, -270, 700, 700); 
    this.xAtual = -400;
    //velocidade da naveAlien
    this.xAdicional = 3; 
    this.xMax = xMax;    // limite da tela
    this.vidas = 3;

    // cria a imagem da naveAlien
    this.element = document.createElement("img");
    this.element.src = "../Imagens/NaveAlien.gif"; 
    this.element.style.position = "absolute";
    this.element.style.left = this.posX + "px";
    this.element.style.top = this.posY + "px";
    this.element.style.width = this.largura + "px";
    this.element.style.height = this.altura + "px";
    this.element.onerror = function() {
    console.log("Erro ao carregar imagem:", aa);
    this.style.display = "none"; 
    };
    document.body.appendChild(this.element);

    //define se a nave morreu
    this.morto = false;
  }

  update() {
    //muda de direção quando toca na borda direita
    if (this.xAtual > this.xMax - 400) {
      this.xAdicional *= -1;
    }

    this.xAtual += this.xAdicional;

    //atualiza a posição
    this.posX = this.xAtual;
    this.element.style.left = this.posX + "px";
    this.element.style.top = this.posY + "px";

    //quando sai pela esquerda remove da tela
    if (this.xAtual < -400) {
      this.remover();
      this.morto = true;
    }

    
  }
  //remove da tela
  remover() {
    this.element.remove();
  }

  //pega a hitbox da naveAlien
  getHitbox() {
    return {
      x: this.posX + 270,
      y: this.posY - 260,
      largura: this.largura - 600,
      altura: this.altura - 140
    };
    
  }
  /*função que faz perder vidas a cada vez que é chamada 
  e remove a naveAlien quando as vidas chegarem a zero */
  PerdeVida(jogo) {
    this.vidas--;
    if (this.vidas <= 0) {
      this.morto = true;
      this.element.remove();
      //pontuação quando destroi a naveAlien
      jogo.score(1000);
    }
  }

}
