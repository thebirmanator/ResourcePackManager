# ResourcePackManager
On-join prompt and reminder to download a resource pack that teleports you to safety to load it, and then sends you back to where you were afterwards. It also removes hotbar items when loading, which bug out sometimes.

## Configuration
There is one configuration file for this plugin, where you can set where to teleport players on resource pack load, and the URL for the resource pack.

Key | Value 
--- | ---
`send-to` | String: '`world\|x\|y\|z\|pitch\|yaw`'
`resource-pack-url` | String: '`www.resourcepack.com`'

It also saves player location and hotbar data below. Please do not edit unless you know what you're doing! It uses Bukkit's built in ItemStack and Location saving formats.

Configuration files:
- [config.yml](https://github.com/thebirmanator/ResourcePackManager/blob/master/src/main/resources/config.yml "Config.yml")
