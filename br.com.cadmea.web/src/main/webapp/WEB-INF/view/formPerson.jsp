<div class="row">
	<div class="input-field col s6">
		<input id="name" ng-model="user.entity.person.name" ng-init="user.entity.person.name='${userSystem.person.name}'" type="text"> 
		<label for="name">Name</label>
	</div>
	<div class="input-field col s6">
	 <input id="dob" type="date" ng-model="user.entity.person.dateOfBirth" ng-init="user.entity.person.dateOfBirth='${userSystem.person.dateOfBirth}'" class="datepicker">
	</div>
</div>

<div class="row">
	<div class="input-field col s12">
		<input id="surname" ng-model="user.entity.person.surname" ng-init="user.entity.person.surname='${userSystem.person.surname}'" type="text"> 
		<label for="surname">Surname</label>
	</div>
</div>	

<div class="row">
	<div class="input-field col s6">
	   <select  id="gender" ng-model="user.entity.person.gender" >
	     <option value="1">Male</option>
	     <option value="2">Female</option>
	   </select>
	   <label for="gender">Gender</label>
 	</div>
 	<div class="input-field col s6">
	    <select  id="relationship" ng-model="user.entity.person.relationship" >
	   	 <option value="" selected>Choose your option</option>
	     <option value="1">Single</option>
	     <option value="2">Married</option>
	     <option value="3">Engagement</option>
	   </select>
	   <label for="relationship">Relationship</label>
 	</div>
</div> 

<div class="row">
	<div class="input-field col s12">
		<select id="country" ng-model="user.entity.person.country.code" >
			<option value="353">Ireland</option>
	     	<option value="55">Brazil</option>
		</select> 
		<label for="country">Country</label>
	</div>
</div>	