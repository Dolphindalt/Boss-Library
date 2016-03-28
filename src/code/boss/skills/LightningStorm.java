package boss.skills;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import boss.BossPlugin;
import boss.utils.CustomEntityFirework;

public class LightningStorm extends Skill {

	private double damage;
	
	private final FireworkEffect.Builder builder = FireworkEffect.builder();
	private final FireworkEffect fe = builder.flicker(true).with(Type.BURST).withColor(Color.AQUA).trail(false).build();
	
	public LightningStorm(double chance, double damage) {
		super(chance);
		this.chance = chance;
		this.damage = damage;
	}
	
	public void run(LivingEntity caster)
	{
		final Location center = caster.getEyeLocation();
		final Location start1 = new Location(center.getWorld(),center.getX()+7,center.getY()+15,center.getZ());
		final Location start2 = start1.clone();
		try
		{
			CustomEntityFirework.spawn(start1, fe);
			Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						CustomEntityFirework.spawn(start1.add(0, 0, 1), fe);
						CustomEntityFirework.spawn(start2.add(0, 0, -1), fe);
						Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									CustomEntityFirework.spawn(start1.add(0, 0, 1), fe);
									CustomEntityFirework.spawn(start2.add(0, 0, -1), fe);
									Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
									{
										@Override
										public void run()
										{
											try
											{
												CustomEntityFirework.spawn(start1.add(-1, 0, 1), fe);
												CustomEntityFirework.spawn(start2.add(-1, 0, -1), fe);
												Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
												{
													@Override
													public void run()
													{
														try
														{
															CustomEntityFirework.spawn(start1.add(0, 0, 1), fe);
															CustomEntityFirework.spawn(start2.add(0, 0, -1), fe);
															Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
															{
																@Override
																public void run()
																{
																	try
																	{
																		CustomEntityFirework.spawn(start1.add(-1, 0, 1), fe);
																		CustomEntityFirework.spawn(start2.add(-1, 0, -1), fe);
																		Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																		{
																			@Override
																			public void run()
																			{
																				try
																				{
																					CustomEntityFirework.spawn(start1.add(-1, 0, 1), fe);
																					CustomEntityFirework.spawn(start2.add(-1, 0, -1), fe);
																					Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																					{
																						@Override
																						public void run()
																						{
																							try
																							{
																								CustomEntityFirework.spawn(start1.add(-1, 0, 0), fe);
																								CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																								Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																								{
																									@Override
																									public void run()
																									{
																										try
																										{
																											CustomEntityFirework.spawn(start1.add(-1, 0, 1), fe);
																											CustomEntityFirework.spawn(start2.add(-1, 0, -1), fe);
																											Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																											{
																												@Override
																												public void run()
																												{
																													try
																													{
																														CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																														CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																														Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																														{
																															@Override
																															public void run()
																															{
																																try
																																{
																																	CustomEntityFirework.spawn(start1.add(-1, 0, 0), fe);
																																	CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																																	Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																	{
																																		@Override
																																		public void run()
																																		{
																																			try
																																			{
																																				CustomEntityFirework.spawn(start1.add(-1, 0, 0), fe);
																																				CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																																				Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																				{
																																					@Override
																																					public void run()
																																					{
																																						try
																																						{
																																							CustomEntityFirework.spawn(start1.add(-1, 0, 0), fe);
																																							CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																																							Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																							{
																																								@Override
																																								public void run()
																																								{
																																									try
																																									{
																																										CustomEntityFirework.spawn(start1.add(-1, 0, -1), fe);
																																										CustomEntityFirework.spawn(start2.add(-1, 0, 1), fe);
																																										Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																										{
																																											@Override
																																											public void run()
																																											{
																																												try
																																												{
																																													CustomEntityFirework.spawn(start1.add(-1, 0, 0), fe);
																																													CustomEntityFirework.spawn(start2.add(-1, 0, 0), fe);
																																													Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																													{
																																														@Override
																																														public void run()
																																														{
																																															try
																																															{
																																																CustomEntityFirework.spawn(start1.add(-1, 0, -1), fe);
																																																CustomEntityFirework.spawn(start2.add(-1, 0, 1), fe);
																																																Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																{
																																																	@Override
																																																	public void run()
																																																	{
																																																		try
																																																		{
																																																			CustomEntityFirework.spawn(start1.add(-1, 0, -1), fe);
																																																			CustomEntityFirework.spawn(start2.add(-1, 0, 1), fe);
																																																			Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																			{
																																																				@Override
																																																				public void run()
																																																				{
																																																					try
																																																					{
																																																						CustomEntityFirework.spawn(start1.add(0, 0, -1), fe);
																																																						CustomEntityFirework.spawn(start2.add(0, 0, 1), fe);
																																																						Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																						{
																																																							@Override
																																																							public void run()
																																																							{
																																																								try
																																																								{
																																																									CustomEntityFirework.spawn(start1.add(-1, 0, -1), fe);
																																																									CustomEntityFirework.spawn(start2.add(-1, 0, 1), fe);
																																																									Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																									{
																																																										@Override
																																																										public void run()
																																																										{
																																																											try
																																																											{
																																																												CustomEntityFirework.spawn(start1.add(0, 0, -1), fe);																																																										
																																																												CustomEntityFirework.spawn(start2.add(0, 0, 1), fe);
																																																												Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																												{
																																																													@Override
																																																													public void run()
																																																													{
																																																														try
																																																														{
																																																															CustomEntityFirework.spawn(start1.add(0, 0, -1), fe);
																																																															CustomEntityFirework.spawn(start2.add(0, 0, 1), fe);
																																																															Bukkit.getScheduler().scheduleSyncDelayedTask(BossPlugin.instance, new Runnable()
																																																															{
																																																																@Override
																																																																public void run()
																																																																{
																																																																	strikeLigtning(center,15,7,caster);
																																																																}
																																																															},(long)1.65);
																																																														}
																																																														catch (Exception e)
																																																														{
																																																															e.printStackTrace();
																																																														}
																																																													}
																																																												},(long)1.65);
																																																											}
																																																											catch (Exception e)
																																																											{
																																																												e.printStackTrace();
																																																											}
																																																										}
																																																									},(long)1.65);
																																																								}
																																																								catch (Exception e)
																																																								{
																																																									e.printStackTrace();
																																																								}
																																																							}
																																																						},(long)1.65);
																																																					}
																																																					catch (Exception e)
																																																					{
																																																						e.printStackTrace();
																																																					}
																																																				}
																																																			},(long)1.65);
																																																		}
																																																		catch (Exception e)
																																																		{
																																																			e.printStackTrace();
																																																		}
																																																	}
																																																},(long)1.65);
																																															}
																																															catch (Exception e)
																																															{
																																																e.printStackTrace();
																																															}
																																														}
																																													},(long)1.65);
																																												}
																																												catch (Exception e)
																																												{
																																													e.printStackTrace();
																																												}
																																											}
																																										},(long)1.65);
																																									}
																																									catch (Exception e)
																																									{
																																										e.printStackTrace();
																																									}
																																								}
																																							},(long)1.65);
																																						}
																																						catch (Exception e)
																																						{
																																							e.printStackTrace();
																																						}
																																					}
																																				},(long)1.65);
																																			}
																																			catch (Exception e)
																																			{
																																				e.printStackTrace();
																																			}
																																		}
																																	},(long)1.65);
																																}
																																catch (Exception e)
																																{
																																	e.printStackTrace();
																																}
																															}
																														},(long)1.65);
																													}
																													catch (Exception e)
																													{
																														e.printStackTrace();
																													}
																												}
																											},(long)1.65);
																										}
																										catch (Exception e)
																										{
																											e.printStackTrace();
																										}
																									}
																								},(long)1.65);
																							}
																							catch (Exception e)
																							{
																								e.printStackTrace();
																							}
																						}
																					},(long)1.65);
																				}
																				catch (Exception e)
																				{
																					e.printStackTrace();
																				}
																			}
																		},(long)1.65);
																	}
																	catch (Exception e)
																	{
																		e.printStackTrace();
																	}
																}
															},(long)1.65);
														}
														catch (Exception e)
														{
															e.printStackTrace();
														}
													}
												},(long)1.65);
											}
											catch (Exception e)
											{
												e.printStackTrace();
											}
										}
									},(long)1.65);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						},(long)1.65);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			},(long)1.65);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}
	
	public void strikeLigtning(Location center, int height, int radius, LivingEntity caster) {
        for(Entity en : center.getWorld().getEntities()) {
        	if(en == null)
        		continue;
        	if(!(en instanceof LivingEntity))
        		continue;
    		if((en).equals(caster))
    			continue;
        	LivingEntity le = (LivingEntity)en;
            center.getWorld().strikeLightningEffect(le.getLocation());
            le.setHealth(le.getHealth() - damage);
            le.damage(0f);
        }
	}
	
	public String getName() {
		return "Lightning Storm";
	}
	
}

