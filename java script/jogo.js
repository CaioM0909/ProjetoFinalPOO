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

    this.direcaoAliens = 1;
    this.velAliens = 50;
    this.contadorAliens = 0; 
    this.contadorDescida = 0;
    this.delayDescida = 100;
    this.delayAliens = 200;
    this.descendo = false;
    this.desceu = 0;
    this.scoreAntigo = 0;
    this.scoreAtual = 0;
    this.naveAlienTimer = null;
    this.desceu = 0;
    this.descendo = false;
    this.derrota = 0

    window.addEventListener("resize", () => this.redimensionaCanvas());
    window.addEventListener("keydown", e => this.teclas[e.code] = true);
    window.addEventListener("keyup", e => this.teclas[e.code] = false);

    this.loop = this.loop.bind(this);
  }
  
  score(pontos) {
    //Adicionando os pontos
    
    this.scoreAtual += pontos;

    //Pontuação do score
    const scoreElemento = document.getElementById("scoreValor");
    if (scoreElemento) {
      scoreElemento.textContent = this.scoreAtual;
    }

    //Verifica qual a maior pontuação
    if (this.scoreAtual > this.scoreAntigo){
      this.scoreAntigo += this.scoreAtual;
    }

    //Pontuação do highscore
     const highElemento = document.getElementById("highValor");
    if (highElemento) {
      highElemento.textContent = this.scoreAntigo;
    }
  }

  redimensionaCanvas() {
    this.largura = window.innerWidth;   
    this.altura = window.innerHeight;
    this.canvas.width = this.largura;
    this.canvas.height = this.altura;

    const jogador = this.objetos.find(e => e instanceof Jogador);
    if (jogador) {
      jogador.posY = this.altura - jogador.altura - 20;
    }
  }

  adicionarTiro(tiro) {
    this.objetos.push(tiro);
  }

  adicionarTiroAlien(tiroAlien) {
    this.objetos.push(tiroAlien);
  }

  iniciar() {
    this.limpezaDados();
    this.derrota = 0;
    this.direcaoAliens = 1;
    const jogador = new Jogador(this.largura / 2, this.altura - 140, this);
    this.objetos.push(jogador);
    this.criarAliens();
    
    


    this.naveAlienTimeout = setTimeout(() => {
      this.criarNaveAlien();
      // cria naveAlien a cada 60 segundos
      this.naveAlienTimer = setInterval(() => {
        this.criarNaveAlien();
      }, 60000);
    }, 10000); // 10 segundos apos o inicio do jogo cria a naveAlien
  
    requestAnimationFrame(this.loop);
    
  }

  criarNaveAlien() {
    //cria o objeto da naveAlien
    const novaNave = new NaveAlien(this.largura);
    this.objetos.push(novaNave);
  }

  //para de criar naves apos o gameover
  pararNaveAlien() {
    if (this.naveAlienTimer) {
      clearInterval(this.naveAlienTimer);
      
    }
    if (this.naveAlienTimeout) {
      clearTimeout(this.naveAlienTimeout);
      this.naveAlienTimeout = null;
    }
  }


  criarAliens() {
      
        let x = 100;
        let y=100;
        for(let j=0;j<5;j++){
            for(let i = 0; i < 12; i++) {
                if(j<2){
                    this.objetos.push(new Alien1(x, y, 500, 500,"/ProjetoFinal/Imagens/alien1.gif"));
                }
                if(j>=2 && j<=3){
                    this.objetos.push(new Alien2(x, y, 500, 500,"/ProjetoFinal/Imagens/alien2.gif"));
                }
                if(j==4){
                    this.objetos.push(new Alien3(x, y, 500, 500,"/ProjetoFinal/Imagens/alien3.gif"));
                }
                x += 80;
            }
            y-=50;
            x=100;
        }
    }

  colisaoTiroJogador(){
      this.objetos.forEach(objeto1 => {
        if (objeto1 instanceof Tiro) {
          for (let objeto2 of this.objetos) {
            if (objeto2 instanceof Alien) {
              if (this.colidiu(objeto1, objeto2)) {
                // remove o alien
                if (objeto2.alien){
                    objeto2.alien.remove();
                    //this.objetos = this.objetos.filter(obj => obj !== objeto2);
                } 
                
                if (objeto2 instanceof Alien1) {
                  this.score(50);
                }
                if (objeto2 instanceof Alien2) {
                  this.score(100);
                }
                if (objeto2 instanceof Alien3) {
                  this.score(200);
                }
            
                // remove o tiro
                objeto1.remover();
                //this.objetos = this.objetos.filter(obj => obj !== objeto1);
                // marca para remoção no próximo filtro
                objeto1.morto = true;
                objeto2.morto = true;

                break;
              }
            }
              // colisão com a nave alien
              if (objeto2 instanceof NaveAlien) {
                if (this.colidiu(objeto1, objeto2)) {
                
                // remove o tiro
                objeto1.remover();
                objeto1.morto = true;

                // subtrai vida da naveAlien
                objeto2.PerdeVida(this);
                
                break;
                }
              }
            }
          }
      });
    }
  

  colisaoTiroAlien(){
      this.objetos.forEach(objeto1 => {
        if (objeto1 instanceof TiroAlien) {
          this.objetos.forEach(objeto2 => {
            if (objeto2 instanceof Jogador) {
              if (this.colidiu(objeto1, objeto2)) {
                  // remove a nave
                  if(objeto2.vidas==1){
                      objeto2.remover();
                      this.objetos = this.objetos.filter(obj => obj !== objeto2);
                      this.derrota=1;
                  }
                  objeto2.vidas--;
                  // remove o tiro
                  objeto1.remover();
                  this.objetos = this.objetos.filter(obj => obj !== objeto1);
                  // marca para remoção no próximo filtro
                  objeto1.morto = true;
              }
            }
          });
        }
      });
  }
      

  loop() {
    this.ctx.clearRect(0, 0, this.largura, this.altura);

      
    this.moveAliens();
      if(this.derrota==1){
        this.limpezaDados();
        this.scoreAtual=0;
        this.score(0);
        this.pararNaveAlien();
        document.getElementById("botaoJogar").style.visibility = "visible";
        document.getElementById("botaoMenu").style.visibility = "visible";
        document.querySelector(".high-count").style.visibility = "visible";
        document.querySelector(".score-count").style.visibility = "hidden";
        document.querySelector(".gameOver").style.visibility = "visible";
        return;
      }

      //Atualiza todos os objetos
      this.objetos.forEach(objeto => {
        if (!(objeto instanceof Alien)) {
          objeto.update(this.teclas, this);
        }
        });


  this.colisaoTiroJogador();
  this.colisaoTiroAlien();

  //remove objetos que estão mortos ou fora da tela
  this.objetos = this.objetos.filter(objeto => {
    if (objeto.morto) {
      return false;
    }
    //remove tiros que sairam da tela
    if (objeto instanceof Tiro && objeto.posY + objeto.altura < 0) {
      objeto.remover();
      return false;
    }
    return true;
  });

  requestAnimationFrame(this.loop);
  }

  colidiu(a, b) {
  let boxA = a.getHitbox ? a.getHitbox() : a;
  let boxB = b.getHitbox ? b.getHitbox() : b;

  return (
    boxA.x < boxB.x + boxB.largura &&
    boxA.x + boxA.largura > boxB.x &&
    boxA.y < boxB.y + boxB.altura &&
    boxA.y + boxA.altura > boxB.y
  );
}

moveAliens(){
      this.descendo=false;
      this.alienMudouDirecao = false;
      this.contadorAliens++;
      const jogador = this.objetos.find(e => e instanceof Jogador);
      if (this.contadorAliens >= this.delayAliens) {
          // Filtra apenas os aliens
          const aliens = this.objetos.filter(o => o instanceof Alien);
          if(aliens.length===0){
            this.criarAliens();
            this.direcaoAliens=1;
          }
          // Calcula posição mínima e máxima dos aliens
          const maxX = Math.max(...aliens.map(a => a.posX + a.largura-160));
          const minX = Math.min(...aliens.map(a => a.posX+160));



          // Se algum alien atingir a borda, muda direção e desce todos
          if (maxX >= this.largura || minX <= 0) {
              this.direcaoAliens *= -1;
              this.descendo=true;
              //Faz os aliens descerem
              aliens.forEach(a => {
                  a.posY += 50; 
                  //Caso chegue a certa altura ele põe como perdido
                  if(a.posY-200 >= jogador.altura){
                      this.derrota=1;
                  }
              });
          }
          // Move todos os aliens
          aliens.forEach(a => {
              if(this.desceu==0){
                  a.posX += this.velAliens * this.direcaoAliens;
              }
            
              // Atualiza posição do elemento <img>
              if(a.alien) {
                  if(this.descendo==false){
                      a.alien.style.left = a.posX + "px";
                  }
                  a.alien.style.top = a.posY + "px";
              }
            });
            //Para fazer com que a posição x deles não aumente em dobro quando estou evitando de mover pros lados ao descer
            if(this.desceu==1){
                this.desceu=0;
            }
            if(this.descendo==true){
                this.desceu=1;
            }
            //Reseta o contador
            if (this.contadorAliens >= this.delayAliens) 
              this.contadorAliens = 0;
            if (aliens.length > 0) {
                const indexAlea = Math.floor(Math.random() * aliens.length);
                const alienEscolhido = aliens[indexAlea];
                this.adicionarTiroAlien(new TiroAlien(alienEscolhido.posX +200, alienEscolhido.posY+200));
            }
            }
  }

  limpezaDados(){
      // limpa a tela
      this.ctx.clearRect(0, 0, this.largura, this.altura);

      // remove elementos (img dos tiros/aliens)
      this.objetos.forEach(obj => {
        if (obj.remover) obj.remover();
      });

      // limpa array
      this.objetos = [];
  }
  
}
