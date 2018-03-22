package com.epiklp.game.functionals;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by Asmei on 2018-03-15.
 */

public class ParticlesManager {
	public static int FIRE = 1;

	private static IntMap<ParticleEffectPool> partyEffectPool;
	private static IntMap<ParticleEffect> partyEffects;

	public ParticlesManager() {
		partyEffectPool = new IntMap<>();
	}

	public static void addParticleEffect(int type, ParticleEffect party, float scale, int startCapacity, int maxCapacity) {
		party.scaleEffect(scale);
		partyEffects.put(type, party);
		partyEffectPool.put(type, new ParticleEffectPool(party, startCapacity, maxCapacity));
	}

	public static ParticleEffectPool.PooledEffect getPooledParticleEffect(int type) {
		return partyEffectPool.get(type).obtain();
	}

	public static void addParticleEffect(int type, ParticleEffect party) {
		addParticleEffect(type, party, 1);
	}

	public static void addParticleEffect(int type, ParticleEffect party, float scale) {
		addParticleEffect(type, party, scale, 5, 20);

	}

	public static ParticleEffect makeParticleEffect(int type, Body body){
		return makeParticleEffect(type, body,0,0);
	}

	public static ParticleEffect makeParticleEffect(int type, Body body, float xo, float yo){
		ParticleEffect particleEffect = getPooledParticleEffect(type);
		particleEffect.setPosition(body.getPosition().x, body.getPosition().y);
		//particleEffect.getEmitters().first().setAttached(true); //manually attach for testing
		return particleEffect;
	}
}
