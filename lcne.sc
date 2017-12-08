LCNE {

	classvar q;

	*start {|user = \default,scope=false|

		q = ();
		NetAddr.broadcastFlag = true;
		q.addrs = (0..7).collect { |x|
			NetAddr("255.255.255.255", 57120 + x)
		};
		q.sendAll = { |q ... args|
			q.addrs.do { |addr|
				addr.sendMsg(*args)
			}; ""
		};

		History.start;
		History.makeWin;

		OSCdef(\hist, { |msg|
			History.enter(msg[2].asString,msg[1]);
		}, \hist).fix;
		History.localOff;

		History.forwardFunc = { |code|
			q.sendAll(\hist, user, code);
		};

		if(scope,{Server.local.scope});

		^"Estas conectado al Ensamble";
	}

	*dupOctave {|escala,octavas = 2|

		var res;

		if(octavas < 1 or: {octavas > 4}, {

			^"Solo puedes usar números del 1 al 4 en el parametro 'octaves'".inform;

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

}
