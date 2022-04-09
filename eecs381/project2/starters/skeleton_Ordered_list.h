/* Ordered_list is a linked-list class template  with iterators similar to the Standard Library std::list class. 
Each list node contains an object of the type specified in the first template parameter, T. 

This is an ordered list in that the nodes are automatically kept in order. The type of the ordering function is a 
a second template parameter, OF (for Ordering Function), whose default value is a type given by a small template 
for a function object class that orders two objects of type T using that type's less-than operator. 
Thus the default is to order objects from smallest to largest using their defined less-than relationship. 
Another template provides a function object class that orders two pointers to objects of type T by 
dereferencing the pointers and applying T's less-than operator.
For example:

	Ordered_list<Thing, Less_than_ref> ol_things; // Thing objects in order by Thing::operator<.
	Ordered_list<Thing> ol_things;	// the same as above, by default
	Ordered_list<Thing *, Less_than_ptrs> ol_things; // Thing pointers in order by Thing::operator<.
	Ordered_list<Thing *, My_ordering_class> ol_things; // Thing pointers in order by a custom ordering.
	
The only way to add to the list is with the insert function, which  automatically puts the new item in the proper place in the list
using the ordering function to determine the point of insertion. 

The iterators encapsulate a pointer to the list nodes, and are a public class nested within the Ordered_list
class, and would be declared e.g. as Ordered_list<Thing *, Less_than_ptr>::Iterator; 
Operators ++, *, and -> are overloaded for iterators similar to std::list<>::iterator. 

Copy constructor and assignment operators are defined, so that Ordered_lists can be used like built-in types.
Move construction and assignment operators are also defined, consistent with C++11 container library practice.
 
To find an object in the list that matches a supplied "probe" object, the ordering function is used to 
to determine equality. That is, the find functions assume that if both (x < y) and (y < x) are false, then x == y. 
This allows both insert and find operations to be done with only the less-than relation.
 
When an object is inserted in the list, a copy is made and assigned into the list node, so
objects stored in the list must have accessible and properly defined copy constructors
and assignment operators.

When a node is removed from the list with erase(), it is destroyed, and so the object contained in the node must
have an accessible and properly defined destructor function.  When the list is cleared with the clear()
function, or destroyed, all of the list nodes are destroyed.

This class does not attempt to protect the list items from being modified. If a list item is
modified in a way that changes where it should appear in the list, the list will become disordered and list items
may become un-findable or new items will be inserted incorrectly - the effects are undefined, although a specific
implementation might behave in a predictable manner.

It is user's responsibility to ensure that items are not changed in a way that affects the validity of 
the ordering in the list. 

If the user declares a list of pointers to objects, the user is responsible for allocating and deallocating 
the pointed-to objects. Note especially that if the Ordered_list is deallocated or cleared, or a 
single node is erased from the list, the pointed-to data is NOT deallocated. In short, the Ordered_list 
does not attempt to manage the user's objects.

If any operations are attempted that are erroneous (e.g. erasing a non-existent node), the
results are undefined. 

This module includes some function templates for applying functions to items in the container,
using iterators to specify the range of items to apply the function to.
*/

/* *** NOTE: If there is a comment "fill this in" remove the comment and replace
it with the proper code here in the header file.  

Comments starting with "***" are instructions to you - remove them from your finished code.
Remove this comment too. */


#ifndef ORDERED_LIST_H
#define ORDERED_LIST_H

#include "p2_globals.h"
#include "Utility.h"
#include <cassert>

// These Function Object Class templates make it simple to use a class's less-than operator
// for the ordering function in declaring an Ordered_list container.

// Compare two objects (passed by const&) using T's operator<
template<typename T>
struct Less_than_ref {
	bool operator() (const T& t1, const T& t2) const {return t1 < t2;}
};

// Compare two pointers (T is a pointer type) using *T's operator<
template<typename T>
struct Less_than_ptr {
	bool operator()(const T p1, const T p2) const {return *p1 < *p2;}
};

// These declare operator() as a const member function because the function does not modify the state of 
// the function object, meaning that it can be used in a const member function of the Ordered_list class.


// A custom ordering function object class is similar, but no template is needed:
//  struct My_ordering_class {
//		bool operator() (const Thing * p1, const Thing * p2) const {return /* whatever you want */;}
//	};




// T is the type of the objects in the list - the data item in the list node
// OF is the ordering function object type, defaulting to Less_than_ref for T
template<typename T, typename OF = Less_than_ref<T> >
class Ordered_list {
		
public:
	// default constructor creates an ordering function object of the OF type.
	Ordered_list();
	
	/* *** Define the constructor, and declare and define the destructor, copy constructor, and assignment operator.
	The destructor must deallocate all nodes. Copy and assignment must produce a list that
	contains nodes that have a copy of the data in the other ordered list. Assignment must use
	the "copy-swap" idiom. 
	
	The move constructor must construct this list by taking the data from
	the original, and leaving it in an empty state (Like the default construction state).
	
	The move assignment operator should take the right-hand side data and leave that
	object in a validly destructible state. The swap function should be used for this purpose.
	
	All constructors and the destructor must increment/decrement g_Ordered_list_count.
	*/
	
	// Delete the nodes in the list, if any, and initialize it. 
	void clear();
	// Return the number of nodes in the list
	int size() const
		{/* fill this in */}
	// Return true if the list is empty
	bool empty() const
		{/* fill this in */}
		
private:
	// Node is a nested class that is private to the Ordered_list<T, OF> class.
	// It is declared here so that Iterator class can refer to it in function
	// definitions in the class declaration.
	struct Node {
		Node(const T& datum_, Node * next_) :
			datum(datum_), next(next_)
			{g_Ordered_list_Node_count++;}
		// copy ctor and dtor defined only to support allocation counting
		Node(const Node& other) :
			datum(other.datum), next(other.next)
			{g_Ordered_list_Node_count++;}
		~Node()
			{g_Ordered_list_Node_count--;}
		T datum;
		Node * next;
		};
		
	public:
	// An Iterator object designates a Node by encapsulating a pointer to the Node, 
	// and provides Standard Library-style operators for using, manipulating, and comparing Iterators.
	// This class is nested inside Ordered_list<> as a public member; refer to as e.g. Ordered_list<int, My_of>::Iterator
	class Iterator {
		public:
			// default initialize to nullptr
			Iterator() :
				node_ptr(nullptr)
				{}
				
			// Overloaded dereferencing operators
			// * returns a reference to the datum in the pointed-to node
			T& operator* () const
				{/* fill this in */}
			// operator-> simply returns the address of the data in the pointed-to node.
			// *** For this operator, the compiler reapplies the -> operator with the returned pointer.
			/* *** definition supplied here because it is a special-case of operator overloading. */
			T* operator-> () const
				{assert(node_ptr); return &(node_ptr->datum);}

			// ++ operator moves the iterator forward to point to the next node; return *this by reference
			Iterator& operator++ ()	// prefix
				{	
					/* fill this in */
				}
			Iterator operator++ (int)	// postfix
				{	
					/* fill this in */
				}
			// Iterators are equal if they point to the same node
			bool operator== (Iterator rhs) const
				{/* fill this in */}
			bool operator!= (Iterator rhs) const
				{/* fill this in */}
	
			// *** here, declare the outer Ordered_list class is a friend		

		private:
			/* *** define here a private constructor for Iterator that takes a Node * parameter.
			Ordered_list can use this to create Iterators conveniently initialized to point to a Node.
			It is private because the client code can't and shouldn't be using it - it isn't even supposed to
			know about the Node objects.  */
			/* *** you may have other private member functions, but not member variables */
			Node * node_ptr;
		};
	// end of nested Iterator class declaration
	
	// return an iterator pointing to the first node
	Iterator begin() const
		{/* fill this in */}
	// return an iterator pointing to "past the end"
	Iterator end() const
		{return Iterator(nullptr);}	// same as next pointer of last node

	// The insert functions add the new datum to the list using the ordering function. 
	// If an "equal" object is already in the list, then the new datum object 
	// is placed in the list before the "equal" one that is already there.
	void insert(const T& new_datum);
	
	// Delete the specified node.
	// Caller is responsible for any required deletion of any pointed-to data beforehand.
	// Do not attempt to dereference the iterator after calling this function - it
	// is invalid after this function executes.
	void erase(Iterator it);

	// The find function returns an iterator designating the node containing the datum that according to
	// the ordering function, is equal to the supplied probe_datum; end() is returned if the node is not found. 
	// If more than one item is equal to the probe, the returned iterator points to the first one.
	// If a matching item is not present, the scan is terminated as soon as possible by detecting 
	// when the scan goes past where the matching item would be.
	Iterator find(const T& probe_datum) const;
	
	// interchange the member variable values of this list with the other list
	void swap(Ordered_list & other);

private:
	// member variable declaration for the ordering function object.
	OF ordering_f;
	/* *** other private member variables and functions are your choice. */
};

// These function templates are given two iterators, usually .begin() and .end(),
// and apply a function to each item (dereferenced iterator) in the list. 
// Note that the function must be an ordinary function; these templates will not
// instantiate if the function is a member function of some class. However, you
// can often use a simple ordinary function with parameters that calls the 
// member function using the parameter values.
 
template<typename IT, typename F>
void apply(IT first, IT last, F function)
{
	for(; first != last; ++first)
		function(*first);
}

// the fourth parameter is used as the second argument of the function
template<typename IT, typename F, typename A>
void apply_arg(IT first, IT last, F function, A arg)
{
// *** fill this in.
}

// this function templates accept the second argument by reference - useful for streams.
template<typename IT, typename F, typename A>
void apply_arg_ref(IT first, IT last, F function, A& arg)
{
// *** fill this in.
}

// the function must return true/false; apply the function until true is returned,
// then return true; otherwise return false.
template<typename IT, typename F>
bool apply_if(IT first, IT last, F function)
{
	for(; first != last; ++first)
		if(function(*first))
			return true;
	return false;
}

// this function works like apply_if, with a fourth parameter used as the second
// argument for the function
template<typename IT, typename F, typename A>
bool apply_if_arg(IT first, IT last, F function, A arg)
{
// *** fill this in.
}

/* *** Put your code for Ordered_list member functions here, defined outside the class declaration.
For example:

template<typename T, typename OF>
void Ordered_list<T, OF>::erase(Iterator it)
{
	your code here
}
 
*/

#endif
