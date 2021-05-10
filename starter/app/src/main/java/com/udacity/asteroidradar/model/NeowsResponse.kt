package com.udacity.asteroidradar.model

data class NeowsResponse(val links : Links,val element_count : Int,val near_earth_objects: Map<String,List<AsteroidResponse>>)
data class Links(val next: String,val prev: String, val self: String)

data class AsteroidResponse(val id : Long,val name : String,val absolute_magnitude_h : Double,val estimated_diameter: EstimatedDiameter,val is_potentially_hazardous_asteroid: Boolean,val close_approach_data : List<CloseApproaachData>) {

    fun convert() : Asteroid {
        return Asteroid(id,name,close_approach_data[0].close_approach_date,absolute_magnitude_h,
            estimated_diameter.kilometers.estimated_diameter_max,close_approach_data[0].relative_velocity.kilometers_per_second,
            close_approach_data[0].miss_distance.astronomical,is_potentially_hazardous_asteroid)
    }
}

data class EstimatedDiameter(val kilometers : EstimatedDiameterLong)

data class EstimatedDiameterLong(val estimated_diameter_min: Double, val estimated_diameter_max : Double)

data class CloseApproaachData(val close_approach_date : String,val relative_velocity: RelativityVelocity,val miss_distance : MissDistance)

data class RelativityVelocity(val kilometers_per_second : Double, val kilometers_per_hour : Double)

data class MissDistance(val astronomical : Double)