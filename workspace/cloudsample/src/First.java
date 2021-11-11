
importjava.io.File; importjava.io.FileNotFoundException; importjava.io.FileOutputStream; importjava.io.IOException; importjava.text.DecimalFormat; importjava.util.ArrayList; importjava.util.Calendar; importjava.util.LinkedList; importjava.util.List; importjava.util.Random; importorg.cloudbus.cloudsim.Cloudlet;
importorg.cloudbus.cloudsim.CloudletSchedulerTimeShared; importorg.cloudbus.cloudsim.Datacenter; importorg.cloudbus.cloudsim.DatacenterBroker; importorg.cloudbus.cloudsim.DatacenterCharacteristics; importorg.cloudbus.cloudsim.Host; importorg.cloudbus.cloudsim.Log; importorg.cloudbus.cloudsim.Pe; importorg.cloudbus.cloudsim.Storage; importorg.cloudbus.cloudsim.UtilizationModel; importorg.cloudbus.cloudsim.UtilizationModelFull;
importorg.cloudbus.cloudsim.Vm; importorg.cloudbus.cloudsim.VmAllocationPolicySimple; importorg.cloudbus.cloudsim.VmSchedulerTimeShared; importorg.cloudbus.cloudsim.core.CloudSim; importorg.cloudbus.cloudsim.provisioners.BwProvisionerSimple; importorg.cloudbus.cloudsim.provisioners.PeProvisionerSimple; importorg.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
public class First {
/** The cloudlet list. */
private static List<Cloudlet>cloudletList;
/** The vmlist. */
private static List<Vm>vmlist;


private static List<Vm>createVM(intuserId, intvms, intidShift) {
//Creates a container to store VMs. This list is passed to the broker later LinkedList<Vm> list = new LinkedList<Vm>();

//VM Parameters
 
long size = 10000; //image size (MB) int ram = 512; //vm memory (MB) intmips = 250;
long bw = 1000;
intpesNumber = 1; //number of cpus String vmm = "Xen"; //VMM name


//create VMs
Vm[] vm = new Vm[vms];


for(inti=0;i<vms;i++){
vm[i] = new Vm(idShift + i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
list.add(vm[i]);

}

return list;
}



private static List<Cloudlet>createCloudlet(intuserId, int cloudlets, intidShift){
// Creates a container to store Cloudlets LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();


//cloudlet parameters long length = 40000; long fileSize = 300; long outputSize = 300; intpesNumber = 1;
UtilizationModelutilizationModel = new UtilizationModelFull(); Cloudlet[] cloudlet = new Cloudlet[cloudlets]; for(inti=0;i<cloudlets;i++){
cloudlet[i] = new Cloudlet(idShift + i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
// setting the owner of these Cloudlets cloudlet[i].setUserId(userId); list.add(cloudlet[i]);
}
 
return list;
}



////////////////////////// STATIC METHODS ///////////////////////

/**
* Creates main() to run this example
*/
public static void main(String[] args) { Log.printLine("Starting Clouddemo...");

try {
// First step: Initialize the CloudSim package. It should be called
// before creating any entities. intnum_user = 2;
// number ofusers
Calendar calendar = Calendar.getInstance(); booleantrace_flag = false; // mean trace events

// Initialize the CloudSim library CloudSim.init(num_user, calendar, trace_flag);

// Second step: Create Datacenters
//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
@SuppressWarnings("unused")
Datacenter datacenter0 = createDatacenter("Datacenter_0");


//Third step: Create Broker
DatacenterBroker broker = createBroker("Broker_0"); intbrokerId = broker.getId();

//Fourth step: Create VMs and Cloudlets and send them to broker vmlist = createVM(brokerId, 2, 0); //creating 2 vms
cloudletList = createCloudlet(brokerId, 8, 0); // creating 8 cloudlets

broker.submitVmList(vmlist); broker.submitCloudletList(cloudletList);
 
// Fifth step: Starts the simulation CloudSim.startSimulation();

// Final step: Print results when simulation is over List<Cloudlet>newList = broker.getCloudletReceivedList();

CloudSim.stopSimulation(); printCloudletList(newList); Log.printLine("Clouddemo finished!");
}

catch (Exception e)
{

e.printStackTrace();
Log.printLine("The simulation has been terminated due to an unexpected error");
}
}


private static Datacenter createDatacenter(Stringname){


// Here are the steps needed to create aPowerDatacenter:
// 1. We need to create a list to store one or more
//	Machines
List<Host>hostList = new ArrayList<Host>();


// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should

//	create a list to store these PEs beforecreating
//	aMachine.
List<Pe> peList1 = new ArrayList<Pe>(); intmips = 1000;
// 3. Create PEs and add these into the list.
//for a dual-core machine, a list of 2 PEs is required:
peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
//4. Create Hosts with its id and list of PEs and add them to the list of machines inthostId=0;
int ram = host storage intbw = 10000;
 
new Host( hostId,
new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList1,
new VmSchedulerSpaceShared(peList1)

)
);


// 5. Create a DatacenterCharacteristics object that stores the

// properties of a data center: architecture, OS, list of
// Machines, allocation policy: time- or space-shared, time zone
// and its price (G$/Pe time unit).
String arch = "x86"; // system architecture String os = "Linux"; // operating system String vmm = "Xen";
double time_zone =10.0;		// time zone this resource located double cost=3.0;	// the cost of using processing in this resource double costPerMem = 0.05; // the cost of using memory in this resource double costPerStorage = 0.1; // the cost of using storage in this resource double costPerBw=0.1;			// the cost of using bw in this resource
LinkedList<Storage>storageList =newLinkedList<Storage>();	//we are not adding SAN devices bynow


DatacenterCharacteristics characteristics = new DatacenterCharacteristics( arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


// 6. Finally, we need to create a PowerDatacenter object. Datacenter datacenter = null;
try {

datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
} catch (Exception e) { e.printStackTrace();

}
 
return datacenter;
}

//to the specific rules of the simulated scenario

private static DatacenterBrokercreateBroker(String name){


DatacenterBroker broker = null; try {
broker = new DatacenterBroker(name);
} catch (Exception e) { e.printStackTrace(); return null;
}
return broker;
}


/**
*	Prints the Cloudletobjects
*	@paramlist list ofCloudlets
*/
private static void printCloudletList(List<Cloudlet> list) { int size = list.size();
Cloudlet cloudlet;


String indent="	"; Log.printLine(); Log.printLine("========== OUTPUT==========");
Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");


DecimalFormatdft = new DecimalFormat("###.##"); for (inti = 0; i< size; i++) {
cloudlet = list.get(i);

Log.print(indent + cloudlet.getCloudletId() + indent + indent); if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){ Log.print("SUCCESS");
Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent
 
+ indent + cloudlet.getVmId() +

 

dft.format(cloudlet.getActualCPUT ime()) +
 
indent + indent + indent +


indent + indent + dft.format(cloudlet.getExecStartTime())+
 
indent + indent + indent + dft.format(cloudlet.getFinishTime()));
}

}
}
}
