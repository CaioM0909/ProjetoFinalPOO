class Jogo {
  constructor(canvasId) {
    this.canvas = document.getElementById(canvasId);
    this.ctx = this.canvas.getContext("2d");

    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    this.objetos = []; // todos os objetos: jogador, aliens, tiros
    this.teclas = {};

    window.addEventListener("resize", () => this.redimensionaCanvas());
    window.addEventListener("keydown", e => this.teclas[e.code] = true);
    window.addEventListener("keyup", e => this.teclas[e.code] = false);

    this.loop = this.loop.bind(this);
  }

  redimensionaCanvas() {
    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    const jogador = this.objetos.find(e => e instanceof Jogador);
    if (jogador) jogador.posY = this.altura - jogador.altura - 20;
  }

  adicionarTiro(tiro) {
    this.objetos.push(tiro);
  }

  iniciar() {
    const jogador = new Jogador(this.largura / 2, this.altura - 120, this);
    this.objetos.push(jogador);
    
    /*const spriteSheet = new Image();
    spriteSheet.src = "alienS1.png";*/

    /*spriteSheet.onload = () => {
    // cria os aliens e inicia o loop
    this.criarAliens(spriteSheet);
    requestAnimationFrame(this.loop);
    }*/
  }

  criarAliens() {
    
    let x = 100;
    for (let i = 0; i < 5; i++) {
      this.objetos.push(new Alien(x, 50, 300, 300));
      x += 80;
    }
  }

  loop() {
    this.ctx.clearRect(0, 0, this.largura, this.altura);

    // Atualiza todos os objetos
    this.objetos.forEach(objeto => objeto.update(this.teclas));

    // Desenha todos os objetos
    //this.objetos.forEach(objeto => objeto.draw(this.ctx));

    // Remove tiros que saíram da tela
    this.objetos = this.objetos.filter(objeto => !(objeto instanceof Tiro && objeto.posY < -50));

    requestAnimationFrame(this.loop);
  }
}
