LCNE {

	classvar <net;
	classvar <array;
	classvar <interval;

	*start {|user = \default, scope = false, meter = false, piranhaV = false|

		net = (); //Diccionario: usando un evento
		NetAddr.broadcastFlag = true;
		// addrs genera una variable en el diccionario (?)
		// crea un array de 8 netaddr
		net.addrs = (0..7).collect { |x|
			NetAddr("255.255.255.255", 57120 + x)
		};
		// sendAll se agrega al diccionario y se iguala a una función
		// la función significa (?)
		net.sendAll = { |net ... args|
			net.addrs.do { |addr|
				addr.sendMsg(*args)
			}; ""
		};
		// arranca History y hace una ventana
		History.start;
		History.makeWin;
		
		// Receptor con el tag \hist
		OSCdef(\hist, { |msg|
			History.enter(msg[2].asString, msg[1]);
		}, \hist).fix;
		
		History.localOff;

		History.forwardFunc = { |code|
			net.sendAll(\hist, user, code);
		};

		// responder array
		OSCdef(\array, {|msg|
			array = msg[1..100].postcln;
		}, \array);

		// responder interval
		OSCdef(\interval, {|msg|
			interval = msg[1].postcln;
		}, \interval);

		if(scope, {Server.local.scope});
		if(meter, {Server.local.meter});


		// Necesita la PiranhaLib.
		// Igual se puede hacer a mano.
		// Esto solamente lo declara quien tiene la ventana de OpenFrameworksX. Por aquí dejo una liga :) https://github.com/rggtrn/PiranhaVivo

		if(piranhaV, {

			PirS.start;
			PirS.n.sendMsg("/textON", 1);

			OSCdef(\ofx, { |msg|
				PirS.n.sendMsg("/message", msg[2].asString, msg[1].asString);
			}, \hist);
		})

		^"Estas conectado al Ensamble";
	}

	*test {
		Pbind(\instrument, \default,
			\dur, 0.5,
			\amp, 0.1,
			\freq, Pseq([200, 200, 400, 400, 600, 600], 1),
			\out, Pseq([0, 1], 6)
		).play;
		^"1, 2, 3 ... probando".inform;
	}

	*dupOctave {|escala, octavas = 2|

		var res;

		if(octavas < 1 or: {octavas > 4}, {

			^"Solo puedes usar números del 1 al 4 en el argumento 'octavas'".inform;

			},{

				switch(octavas,
					1,{
						res = escala;
						^res.postln;
					},
					2,{
						res = escala++(escala+12);
						^res.postln;
					},
					3,{
						res = escala++(escala+12)++(escala+24);
						^res.postln;
					},
					4,{
						res = escala++(escala+12)++(escala+24)++(escala+36);
						^res.postln;
					}

				);
		});

	}

	*ventana{
		
		^"Mensajes".inform;
	}

    *arreglo{|arreglo=#[1, 2, 3]|
		this.net.sendAll(\array, *arreglo)
		^"array".inform;
	}

	*intervalo{|interval = 1|
		this.net.sendAll(\interval, interval);
		^"interval".inform;
	}
}
