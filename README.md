# FarmingPluginSpigot
FarmingPlugin is a Minecraft Java Edition Plugin designed to add new yield generation for farming.
It replaces the current standard break-and-receive structure, adds a qualifier of having broken with a hoe, and returns inputs depending upon its contents. 

##Usage
The plugin contains event listeners for crop yield, as well as for attempted workaround prevention.
It contains permission commands which check for moderator status to add an additional permission disabling or enabling crop trampling upon jumping.

### Farming
The event listeners pertaining to farming qualify a valid harvest as having used a wooden, stone, iron, gold or diamond hoe.
Hoes are broken into tiers of yield, with stone and wood yielding the lowest, iron and gold the median, and diamond the highest yield.

#### Yields
The Yields all return a minimum of one seed. The yields are returned via a random amount with a floor of one and a maximum amount set by the tier.
Crops follow a similar process, though not random and instead a fixed amount.

### Glitch Prevention
The plugin prevents breaking the block below a crop, exploding it with TNT or using Pistons to displace crops, mob trampling, water and lava, as well as creeper griefing.

### Commands
Plugin can write to a yml file that, if a username is contained within, prevents trampling of crops via a listener.


