/* Meeting class - this class represents a Meeting in terms of a time, topic, and 
list of participants. Public functions provide for maintaining the list of participants,
but no direct access to it is allowed. 
An overloaded operator< is provided to simplify constructing a list of Meetings 
in order by time, and an output operator to simplify printing the Meeting information.
A static int member variable keeps count of the number of Meeting objects in existence.
*/

/* *** NOTE: If after a function header is a comment "fill this in" remove the comment and replace
it with the proper code here in the header file.  All other functions should be defined
in the .cpp file. 
Comments starting with "***" are instructions to you - remove them from your finished code.
Remove this comment too. */

public:
	Meeting(int time_, const String& topic_)
		/*fill this in*/
	// construct a Meeting with only a time
	Meeting(int time_)
		/*fill this in*/
	// Construct a Meeting from an input file stream in save format
	// Throw Error exception if invalid data discovered in file.
	// No check made for whether the Meeting already exists or not.
	// Person list is needed to resolve references to meeting participants
	// Input for a member variable value is read directly into the member variable.
	Meeting(std::ifstream& is, const Ordered_list<const Person *, Less_than_ptr<const Person *> >& people);
	// The following are defined only because we are counting Meeting objects created or destroyed.
    // Normally the default compiler-generated member functions would work correct.
    // If we want move construction or assignment, we have to explicitly specify it because they will be
    // disabled otherwise.
	Meeting(const Meeting& original);
    Meeting(Meeting&& original);
    Meeting& operator= (const Meeting& rhs) = default;  // compiler supplied version will work
    Meeting& operator= (Meeting&& rhs) = default;   // compiler supplied version will work

	~Meeting()
		{/*fill this in*/}
	// accessors
	int get_time() const
		{/*fill this in*/}
	void set_time(int time_)
		{/*fill this in*/}
	String get_topic() const
		{/*fill this in*/}
	// return the number of Meeting objects
	static int get_number()
		{/*fill this in*/}

	// Meeting objects manage their own participant list. Participants
	// are identified by a unique pointer to that individual's Person object.

	// Add to the list, throw exception if participant was already there.
	void add_participant(const Person * p);
	// Return true if the person is a participant, false if not.
	bool is_participant_present(const Person * p) const;
	// Remove from the list, throw exception if participant was not found.
	void remove_participant(const Person * p);
			
	// Write a Meeting's data to a stream in save format with final endl.
	void save(std::ostream& os) const;

	// This operator defines the order relation between meetings, based just on the time
	bool operator< (const Meeting& other) const;
	
	/* *** fill in here the appropriate declaration for the output operator */
		
private:
	/* *** the participant information must be kept in a container of const Person *   */
	typedef Ordered_list<const Person *, Less_than_ptr<const Person *> > Participants_t;
	Participants_t participants;
	/* *** declare a static int member variable for the count of the number of Meeting objects */
	/* *** other private members are your choice */
};

// Print the Meeting data as follows:
// The meeting time and topic on one line, followed either by:
// the no-participants message with an endl
// or the partipcants, one per line, with an endl after each one
std::ostream& operator<< (std::ostream& os, const Meeting& meeting);
