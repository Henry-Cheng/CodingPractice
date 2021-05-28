# Yelp or Nearby Friends

Read heavy service.

## Critical feature

- display Point of interest on map based on user location
- POI can be aggregated by category
- user can view details of each POI
- user can upload comment of each POI


## Location server

1. Lattitude/Longitude
  - range query based on lattitude/longitude
  - pros:
    - uniquely identify each POI
  - cons:
    - scan the full table exery time
    - POI table can be very large
2. GeoHash (https://aws.amazon.com/blogs/mobile/geo-library-for-amazon-dynamodb-part-1-table-structure/)
  - whole world is divided into rectangles
    - e.g. for US uses "9"
  - the string is unique across the whole world
  - it likes a prefix-tree, more character is more accurate
3. Geospatial database
  - using r-tree to split the world into lots of rectange, using n-array search 
  - pros:
    - optimized index query based on distance/shape/etc.
  - cons:
    - 
4. 

