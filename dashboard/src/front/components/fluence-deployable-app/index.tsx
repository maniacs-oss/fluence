import * as React from 'react';
import {connect} from 'react-redux';
import {DeployableApp, DeployableAppId, deployableApps} from "../../../fluence/deployable";
import {deploy} from "../../actions/deployable/deploy";
import {Action} from "redux";
import Snippets from "./snippets";

interface State {
    loading: boolean,
}

interface Props {
    id: DeployableAppId,
    deployableApps: {
        [key: string]: DeployableApp
    },
    deploy: (app: DeployableApp) => Promise<Action>
}

class FluenceDeployableApp extends React.Component<Props, State> {
    state: State = {
        loading: false,
    };

    startDeploy = (e: React.MouseEvent<HTMLElement>, app: DeployableApp) => {
        this.setState({loading: true});
        this.props.deploy(app)
            .then(() => this.setState({loading: false}))
            .catch(e => {
                console.error("error while deploying " + JSON.stringify(e));
                this.setState({loading: false});
            });
    };

    renderAppInfo(app: DeployableApp): React.ReactNode {
        return (
            <div className="box-footer no-padding">
                <div className="box-body">
                    <strong><i className="fa fa-bullseye margin-r-5"/>Storage Hash</strong>
                    <p className="text-muted" title={app.storageHash}>{app.storageHash}</p>
                    <hr/>

                    <strong><i className="fa fa-bullseye margin-r-5"/>Cluster Size</strong>
                    <p className="text-muted">{app.clusterSize}</p>
                    <hr/>

                    <p>
                        <button
                            type="button"
                            onClick={e => this.startDeploy(e, app)}
                            className="btn btn-block btn-primary">
                            Deploy app <i style={{display: this.state.loading ? 'inline-block' : 'none'}}
                                          className="fa fa-refresh fa-spin"/>
                        </button>
                    </p>
                </div>
            </div>
        );
    }

    render(): React.ReactNode {
        const app = this.props.deployableApps[this.props.id];

        return (
            <div>
                <div className="col-md-4 col-xs-12">
                    <div className="box box-widget widget-user-2">
                        <div className="widget-user-header bg-fluence-blue-gradient">
                            <div className="widget-user-image">
                            <span className="entity-info-box-icon">
                                <i className={app ? 'ion ion-ios-gear-outline' : 'fa fa-refresh fa-spin'}/>
                            </span>
                            </div>
                            <h3 className="widget-user-username">{this.props.deployableApps[this.props.id].name}</h3>
                        </div>
                        {app && this.renderAppInfo(app)}
                    </div>
                </div>
                <div className="col-md-4 col-xs-12">
                    <Snippets/>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state: any) => ({
    deployableApps: deployableApps,
});

const mapDispatchToProps = {
    deploy
};

export default connect(mapStateToProps, mapDispatchToProps)(FluenceDeployableApp);