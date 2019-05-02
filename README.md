# jedi-order
UNIQUE ID generator

Inspired by Twitter's Snowflake, refer to [Vesta](https://github.com/cloudatee/vesta-id-generator "vesta").

ID is composed of

	 1 bit for version
	41 bits for time(ms)
	10 bits for a machine id
	12 bits for a sequence number

