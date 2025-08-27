class Jogo {
  constructor() {
    this.canvas = document.getElementById("Canvas");
    this.ctx = this.canvas.getContext("2d");
    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    this.redimensionaCanvas(); //Redimensiona a tela

    this.player = null;
    this.aliens = [];
    this.tiros = [];
    this.teclas = {};

    this.loop = this.loop.bind(this);

    window.addEventListener("resize", () => this.redimensionaCanvas());
  }


  

  redimensionaCanvas() {
    this.largura = window.innerWidth;
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    
    if (this.player) {
      this.player.posY = this.altura - 120;
      if (this.player.posX > this.largura - 100) {
        this.player.posX = this.largura - 100;
      }
    }
  }

  iniciar() {
    this.player = new Jogador(this.largura / 2, this.altura - 120, this);
    this.criarAliens();

    window.addEventListener("keydown", e => this.teclas[e.code] = true);
    window.addEventListener("keyup", e => this.teclas[e.code] = false);

    requestAnimationFrame(this.loop);
  }

  criarAliens() {
    let numAliens = Math.floor(this.largura / 150);
    let x = 400, y1 = 50, y2 = 100, y3 = 150, y4 = 200, y5 = 250;

    for (let i = 0; i < numAliens; i++) {
      this.aliens.push(new Alien(x, y1, "Imagens/alien3.gif"));
      this.aliens.push(new Alien(x, y2, "Imagens/alien2.gif"));
      this.aliens.push(new Alien(x, y3, "Imagens/alien2.gif"));
      this.aliens.push(new Alien(x, y4, "Imagens/alien1.gif"));
      this.aliens.push(new Alien(x, y5, "Imagens/alien1.gif"));
      x += 80;
    }
  }

  loop() {
    this.ctx.clearRect(0, 0, this.largura, this.altura);

    // Atualiza e desenha jogador
    this.player.update(this.teclas);
    this.player.draw(this.ctx);

    // Atualiza e desenha tiros
    this.tiros.forEach((tiro, index) => {
      tiro.update();
      tiro.draw(this.ctx);
      if (tiro.posY < -50) this.tiros.splice(index, 1);

      // colisão com alien
      this.aliens.forEach((alien, i) => {
        if (tiro.colideCom(alien)) {
          this.aliens.splice(i, 1);
          this.tiros.splice(index, 1);
        }
      });
    });

    // Atualiza e desenha aliens
    this.aliens.forEach(alien => alien.draw(this.ctx));

    requestAnimationFrame(this.loop);
  }
}

class Jogador {
  constructor(x, y, jogo) {
    this.posX = x;
    this.posY = y;
    this.vel = 6;
    this.sprite = new Image();
    this.sprite.src = "Imagens/gifNave.gif";
    this.jogo = jogo;
    this.cooldown = 0;
  }

  update(teclas) {
    if (teclas["KeyD"]) this.posX += this.vel;
    if (teclas["KeyA"]) this.posX -= this.vel;
    if (teclas["Space"] && this.cooldown <= 0) {
      this.jogo.tiros.push(new Tiro(this.posX, this.posY));
      this.cooldown = 20; // tempo entre tiros
    }
    if (this.cooldown > 0) this.cooldown--;
  }

  draw(ctx) {
    ctx.drawImage(this.sprite, this.posX - 50, this.posY, 100, 100);
  }
}

class Tiro {
  constructor(x, y) {
    this.posX = x;
    this.posY = y;
    this.vel = 10;
    this.sprite = new Image();
    this.sprite.src = "Imagens/tiroNave.gif";
  }

  update() {
    this.posY -= this.vel;
  }

  draw(ctx) {
    ctx.drawImage(this.sprite, this.posX - 10, this.posY, 20, 40);
  }

  colideCom(alien) {
    return (
      this.posX > alien.posX &&
      this.posX < alien.posX + alien.largura &&
      this.posY > alien.posY &&
      this.posY < alien.posY + alien.altura
    );
  }
}

class Alien {
  constructor(x, y, spritePath) {
    this.posX = x;
    this.posY = y;
    this.largura = 350;
    this.altura = 350;
    this.sprite = new Image();
    this.sprite.src = spritePath;
  }

  draw(ctx) {
    ctx.drawImage(this.sprite, this.posX, this.posY, this.largura, this.altura);
  }
}

// === Controle inicial ===
const jogo = new Jogo();
document.getElementById("botaoJogar").onclick = () => {
  document.getElementById("botaoJogar").style.display = "none";
  document.getElementById("titulo").style.display = "none";
  jogo.iniciar();
};
